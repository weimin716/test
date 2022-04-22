package com.gdd.ylz.modules.mail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdd.ylz.common.util.DateUtils;
import com.gdd.ylz.modules.mail.dao.MsgMapper;
import com.gdd.ylz.modules.mail.dto.EmailMsgDTO;
import com.gdd.ylz.modules.mail.dto.MsgQueryDTO;
import com.gdd.ylz.modules.mail.entity.Msg;
import com.gdd.ylz.modules.mail.service.IMsgService;
import com.gdd.ylz.modules.sys.entity.Filestore;
import com.gdd.ylz.modules.sys.entity.TPlattUser;
import com.gdd.ylz.modules.sys.service.IFilestoreService;
import com.gdd.ylz.modules.sys.service.ITPlattUserService;
import com.gdd.ylz.result.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 邮件信息 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2021-12-22
 */
@Service
@Slf4j
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements IMsgService {
    @Autowired
    private IFilestoreService filestoreService;
    @Autowired
    private MsgMapper msgMapper;
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private IMsgService msgService;
    @Autowired
    private ITPlattUserService plattUserService;

    private synchronized JavaMailSenderImpl getJavaMailSender(TPlattUser plattUser) {
        // 获取邮箱发送实例
        String host = "localhost";
        String username = plattUser.getEmail();
        String password = plattUser.getMailPwd();
        String sslPort = plattUser.getPort();
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost(host);
        javaMailSenderImpl.setUsername(username);
        javaMailSenderImpl.setPassword(password);
        javaMailSenderImpl.setDefaultEncoding("UTF-8");
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.port", sslPort);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailSenderImpl.setJavaMailProperties(properties);
        return javaMailSenderImpl;
    }

    @Override
    public boolean sendSimpleMail(EmailMsgDTO msg, TPlattUser plattUser) {
        log.info("简单邮件开始发送");
        try {
            JavaMailSenderImpl javaMailSender = getJavaMailSender(plattUser);
            String username = javaMailSender.getUsername();
            log.info("当前发送邮箱: " + username);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(username);
            message.setTo(msg.getToEmails().split(";"));
            message.setSubject(msg.getSubject());
            message.setText(msg.getContent());
            javaMailSender.send(message);
            log.info("简单邮件发送成功");
            return true;
        } catch (Exception e) {
            log.error("简单邮件发送异常", e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public DataResult sendHtmlMail(EmailMsgDTO msg, TPlattUser plattUser) {
        log.info("HTML邮件开始发送");
        try {
            JavaMailSenderImpl javaMailSender = getJavaMailSender(plattUser);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            String username = javaMailSender.getUsername();
            log.info("当前发送邮箱: " + username);
            messageHelper.setFrom(username);
            messageHelper.setTo(msg.getToEmails().split(";"));
            messageHelper.setSubject(msg.getSubject());
            messageHelper.setText(msg.getContent(), true);
            List<Filestore> filestores = filestoreService.listByIds(msg.getAttachmentsIds());
            String attIds = StringUtils.join(msg.getAttachmentsIds().toArray(), ";");
            for (Filestore filestore : filestores) {
                messageHelper.addAttachment(filestore.getOriginalName(), new FileSystemResource(new File(filestore.getFilePath())));
            }
            javaMailSender.send(message);
            Msg msgsave = new Msg();
            BeanUtils.copyProperties(msg, msgsave);
            msgsave.setStatus(5);
            msgsave.setAttachments(attIds);
            msgsave.setType(0);
            msgsave.setFromEmails(username);
            msgsave.setFromName(plattUser.getNickname() + "<" + username + ">");
            msgsave.setCreateBy(plattUser.getId());
            msgsave.setSendTime(new Date());
            msgsave.setCreateTime(new Date());
            this.save(msgsave);
            log.info("HTML邮件发送成功");
            return DataResult.success();
        } catch (Exception e) {
            log.error("HTML邮件发送异常", e);
            e.printStackTrace();
        }
        return DataResult.error();
    }

    @Override
    public DataResult getAlreadySendCount(String userId) {
        Integer count = msgMapper.selectCount(new QueryWrapper<Msg>().lambda().eq(Msg::getCreateBy, userId).eq(Msg::getStatus, 5));
        return DataResult.success(count);

    }

    @Override
    public DataResult getSendEmailList(MsgQueryDTO msgQueryDTO, String userId) {
        Page page = new Page(msgQueryDTO.getStart(), msgQueryDTO.getLength());
        msgQueryDTO.setUserId(userId);
        return DataResult.success(msgMapper.getSendEmailList(page, msgQueryDTO));
    }

    @Override
    public DataResult getHtmlMails(TPlattUser plattUser){
        Date startDate=plattUser.getRecieveDate();
        Date endDate=new Date();

        // 准备连接服务器的会话信息
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", "localhost");
        props.setProperty("mail.pop3.port", "110");

        // 创建Session实例对象
        Session session = Session.getInstance(props);

        // 创建IMAP协议的Store对象
        Store store = null;
        try {
            store = session.getStore("pop3");
            // 连接邮件服务器
            store.connect(plattUser.getEmail(), plattUser.getMailPwd());

            // 获得收件箱
            Folder folder = store.getFolder("INBOX");
            // 以读写模式打开收件箱
            folder.open(Folder.READ_ONLY);

            // 获得收件箱的邮件列表
            Message[] messages = folder.getMessages();
            List<Message> messagesList = Arrays.asList(messages);


            // 打印不同状态的邮件数量
            System.out.println("收件箱中共" + messages.length + "封邮件!");
            System.out.println("收件箱中共" + folder.getUnreadMessageCount() + "封未读邮件!");
            System.out.println("收件箱中共" + folder.getNewMessageCount() + "封新邮件!");
            System.out.println("收件箱中共" + folder.getDeletedMessageCount() + "封已删除邮件!");

            System.out.println("------------------------开始解析邮件----------------------------------");


            int total = folder.getMessageCount();
            System.out.println("-----------------您的邮箱共有邮件：" + total + " 封--------------");
            // 得到收件箱文件夹信息，获取邮件列表
            List<Message> collectMessage = messagesList.stream().filter(o -> {
                boolean flag = false;
                try {
                    flag = o.getSentDate().after(startDate) && o.getSentDate().before(endDate);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                return flag;
            }).collect(Collectors.toList());
            Message[] msgs = folder.getMessages();
            List<Msg> msgList=new ArrayList<>();
            System.out.println("\t收件箱的总邮件数：" + msgs.length);
            for (int i = 0; i < collectMessage.size(); i++) {
                MimeMessage msg = (MimeMessage) collectMessage.get(i);
                System.out.println("------------------解析第" + msg.getMessageNumber() + "封邮件-------------------- ");
                System.out.println("主题: " + getSubject(msg));
                System.out.println("发件人: " + getFrom(msg));
                System.out.println("收件人：" + getReceiveAddress(msg, null));
                System.out.println("发送时间：" + getSentDate(msg, "yyyy-MM-dd HH:mm:ss"));
                System.out.println("是否已读：" + isSeen(msg));
                System.out.println("邮件优先级：" + getPriority(msg));
                System.out.println("是否需要回执：" + isReplySign(msg));
                System.out.println("邮件大小：" + msg.getSize() * 1024 + "kb");
                Msg msgSave=new Msg();
                msgSave.setSubject(getSubject(msg));
                msgSave.setFromEmails(getFromAddress(msg));
                msgSave.setFromName(getFrom(msg));
                msgSave.setToEmails(getReceiveAddress(msg, null));
                msgSave.setSendTime(DateUtils.parseDate(getSentDate(msg, "yyyy-MM-dd HH:mm:ss")));
                msgSave.setIsRead(0);
                msgSave.setStatus(0);
                boolean isContainerAttachment = isContainAttachment(msg);
                System.out.println("是否包含附件：" + isContainerAttachment);
                List<String> fileIds=new ArrayList<>();
                if (isContainerAttachment) {
                    saveAttachment(msg,fileIds); //保存附件
                }
                StringBuffer content = new StringBuffer(30);
                getMailTextContent(msg, content);
                msgSave.setContent(content.toString());
                msgSave.setStatus(0);
                msgSave.setCreateBy(plattUser.getId());
                if(!CollectionUtils.isEmpty(fileIds)){
                    msgSave.setAttachments(StringUtils.join(fileIds.toArray(), ";"));
                }
                System.out.println("邮件正文：" + (content.length() > 100 ? content.substring(0,100) + "..." : content));
                System.out.println("------------------第" + msg.getMessageNumber() + "封邮件解析结束-------------------- ");
                System.out.println();
//                System.out.println(a.getSubject() + "   接收时间：" + a.getReceivedDate().toLocaleString()+"  contentType()" +a.getContentType());
              msgList.add(msgSave);
            }
            System.out.println("\t未读邮件数：" + folder.getUnreadMessageCount());
            System.out.println("\t新邮件数：" + folder.getNewMessageCount());
            System.out.println("----------------End------------------");

            if(!CollectionUtils.isEmpty(msgList)){
                msgService.saveBatch(msgList);
            }
            plattUser.setRecieveDate(endDate);
            plattUserService.updateById(plattUser);
            // 关闭资源
            folder.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       return DataResult.success();

    }

    @Override
    @Transactional
    public DataResult getRecieveEmailList(MsgQueryDTO msgQueryDTO, TPlattUser plattUser) {
        //收取信息存入数据库
        msgQueryDTO.setUserId(plattUser.getId());
        getHtmlMails(plattUser);
        //查询列表返回
        Page page =new Page(msgQueryDTO.getStart(),msgQueryDTO.getLength());
        return DataResult.success(msgMapper.getRecieveEmailList(page,msgQueryDTO));
    }


    /**
     * 获得邮件主题
     * @param msg 邮件内容
     * @return 解码后的邮件主题
     */
    public static String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {
        return MimeUtility.decodeText(msg.getSubject());
    }

    /**
     * 获得邮件发件人
     * @param msg 邮件内容
     * @return 姓名 <Email地址>
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
        String from = "";
        Address[] froms = msg.getFrom();
        if (froms.length < 1)
            throw new MessagingException("没有发件人!");

        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();
        if (person != null) {
            person = MimeUtility.decodeText(person) + " ";
        } else {
            person = "";
        }
        from = person + "<" + address.getAddress() + ">";

        return from;
    }

    /**
     * 获得邮件发件人
     * @param msg 邮件内容
     * @return 姓名 <Email地址>
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static String getFromAddress(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
        String from = "";
        Address[] froms = msg.getFrom();
        if (froms.length < 1)
            throw new MessagingException("没有发件人!");

        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();
        if (person != null) {
            person = MimeUtility.decodeText(person) + " ";
        } else {
            person = "";
        }
        from = address.getAddress();

        return from;
    }


    /**
     * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
     * <p>Message.RecipientType.TO  收件人</p>
     * <p>Message.RecipientType.CC  抄送</p>
     * <p>Message.RecipientType.BCC 密送</p>
     * @param msg 邮件内容
     * @param type 收件人类型
     * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ...
     * @throws MessagingException
     */
    public static String getReceiveAddress(MimeMessage msg, Message.RecipientType type) throws MessagingException {
        StringBuffer receiveAddress = new StringBuffer();
        Address[] addresss = null;
        if (type == null) {
            addresss = msg.getAllRecipients();
        } else {
            addresss = msg.getRecipients(type);
        }

        if (addresss == null || addresss.length < 1)
            throw new MessagingException("没有收件人!");
        for (Address address : addresss) {
            InternetAddress internetAddress = (InternetAddress)address;
            receiveAddress.append(internetAddress.toUnicodeString()).append(";");
        }

        receiveAddress.deleteCharAt(receiveAddress.length()-1);	//删除最后一个逗号

        return receiveAddress.toString();
    }

    /**
     * 获得邮件发送时间
     * @param msg 邮件内容
     * @return yyyy年mm月dd日 星期X HH:mm
     * @throws MessagingException
     */
    public static String getSentDate(MimeMessage msg, String pattern) throws MessagingException {
        Date receivedDate = msg.getSentDate();
        if (receivedDate == null)
            return "";

        if (pattern == null || "".equals(pattern))
            pattern = "yyyy年MM月dd日 E HH:mm ";

        return new SimpleDateFormat(pattern).format(receivedDate);
    }

    /**
     * 判断邮件中是否包含附件
     * @param part 邮件内容
     * @return 邮件中存在附件返回true，不存在返回false
     * @throws MessagingException
     * @throws IOException
     */
    public static boolean isContainAttachment(Part part) throws MessagingException, IOException {
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = isContainAttachment(bodyPart);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("application") != -1) {
                        flag = true;
                    }

                    if (contentType.indexOf("name") != -1) {
                        flag = true;
                    }
                }

                if (flag) break;
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = isContainAttachment((Part)part.getContent());
        }
        return flag;
    }

    /**
     * 判断邮件是否已读
     * @param msg 邮件内容
     * @return 如果邮件已读返回true,否则返回false
     * @throws MessagingException
     */
    public static boolean isSeen(MimeMessage msg) throws MessagingException {
        return msg.getFlags().contains(Flags.Flag.SEEN);
    }

    /**
     * 判断邮件是否需要阅读回执
     * @param msg 邮件内容
     * @return 需要回执返回true,否则返回false
     * @throws MessagingException
     */
    public static boolean isReplySign(MimeMessage msg) throws MessagingException {
        boolean replySign = false;
        String[] headers = msg.getHeader("Disposition-Notification-To");
        if (headers != null)
            replySign = true;
        return replySign;
    }

    /**
     * 获得邮件的优先级
     * @param msg 邮件内容
     * @return 1(High):紧急  3:普通(Normal)  5:低(Low)
     * @throws MessagingException
     */
    public static String getPriority(MimeMessage msg) throws MessagingException {
        String priority = "普通";
        String[] headers = msg.getHeader("X-Priority");
        if (headers != null) {
            String headerPriority = headers[0];
            if (headerPriority.indexOf("1") != -1 || headerPriority.indexOf("High") != -1)
                priority = "紧急";
            else if (headerPriority.indexOf("5") != -1 || headerPriority.indexOf("Low") != -1)
                priority = "低";
            else
                priority = "普通";
        }
        return priority;
    }

    /**
     * 获得邮件文本内容
     * @param part 邮件体
     * @param content 存储邮件文本内容的字符串
     * @throws MessagingException
     * @throws IOException
     */
    public static void getMailTextContent(Part part, StringBuffer content) throws MessagingException, IOException {
        //如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getMailTextContent((Part)part.getContent(),content);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailTextContent(bodyPart,content);
            }
        }
    }

    /**
     * 保存附件
     * @param part 邮件中多个组合体中的其中一个组合体
     * @param destDir  附件保存目录
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public  void saveAttachment(Part part,List<String> fileIds) throws UnsupportedEncodingException, MessagingException,
            FileNotFoundException, IOException {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();	//复杂体邮件
            //复杂体邮件包含多个邮件体
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                //获得复杂体邮件中其中一个邮件体
                BodyPart bodyPart = multipart.getBodyPart(i);
                //某一个邮件体也有可能是由多个邮件体组成的复杂体
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                    InputStream is = bodyPart.getInputStream();
                    String suffix = "." + FilenameUtils.getExtension(bodyPart.getFileName());
                    String newname = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + com.gdd.ylz.common.util.StringUtils.getGUID() + suffix;
                    int size= (int) bodyPart.getSize();
                    String relativePath=uploadPath+"/"+DateUtils.formatDate(new Date(), "yyyyMMdd");
                    String dirpath=System.getProperty("user.dir")+"/"+relativePath;
                    Filestore filestore=new Filestore();
                    String id= com.gdd.ylz.common.util.StringUtils.getGUID();
                    filestore.setId(id);
                    filestore.setFileId(id);
                    filestore.setFilePath(dirpath+"/"+newname);
                    filestore.setRelativePath(relativePath+"/"+newname);
                    filestore.setFileSize(size);
                    filestore.setFileSuffix(suffix);
                    filestore.setNewName(newname);
                    filestore.setOriginalName(bodyPart.getFileName());
                    filestore.setDownCounts(BigDecimal.ZERO);
                    filestore.setFileType(bodyPart.getContentType());
                    saveFile(is, dirpath, decodeText(bodyPart.getFileName()));
                    filestoreService.save(filestore);
                    fileIds.add(id);

                } else if (bodyPart.isMimeType("multipart/*")) {
                    saveAttachment(bodyPart,fileIds);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {
                        String suffix = "." + FilenameUtils.getExtension(bodyPart.getFileName());
                        String newname = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + com.gdd.ylz.common.util.StringUtils.getGUID() + suffix;
                        int size= (int) bodyPart.getSize();
                        String relativePath=uploadPath+"/"+DateUtils.formatDate(new Date(), "yyyyMMdd");
                        String dirpath=System.getProperty("user.dir")+"/"+relativePath;
                        Filestore filestore=new Filestore();
                        String id= com.gdd.ylz.common.util.StringUtils.getGUID();
                        filestore.setId(id);
                        filestore.setFileId(id);
                        filestore.setFilePath(dirpath+"/"+newname);
                        filestore.setRelativePath(relativePath+"/"+newname);
                        filestore.setFileSize(size);
                        filestore.setFileSuffix(suffix);
                        filestore.setNewName(newname);
                        filestore.setOriginalName(bodyPart.getFileName());
                        filestore.setDownCounts(BigDecimal.ZERO);
                        saveFile(bodyPart.getInputStream(), dirpath, decodeText(bodyPart.getFileName()));
                        filestoreService.save(filestore);
                        fileIds.add(id);
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachment((Part) part.getContent(),fileIds);
        }
    }

    /**
     * 读取输入流中的数据保存至指定目录
     * @param is 输入流
     * @param fileName 文件名
     * @param destDir 文件存储目录
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void saveFile(InputStream is, String destDir, String fileName)
            throws FileNotFoundException, IOException {
        File file = new File(destDir + fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(file));
        int len = -1;
        while ((len = bis.read()) != -1) {
            bos.write(len);
            bos.flush();
        }
        bos.close();
        bis.close();
    }

    /**
     * 文本解码
     * @param encodeText 解码MimeUtility.encodeText(String text)方法编码后的文本
     * @return 解码后的文本
     * @throws UnsupportedEncodingException
     */
    public static String decodeText(String encodeText) throws UnsupportedEncodingException {
        if (encodeText == null || "".equals(encodeText)) {
            return "";
        } else {
            return MimeUtility.decodeText(encodeText);
        }
    }



}

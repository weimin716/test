package com.gdd.ylz.test;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.gdd.ylz.YlzApplicationTests;
import com.gdd.ylz.modules.excel.ContactCellWriteHandler;
import com.gdd.ylz.modules.excel.ContactSheetWriteHandler;
import com.gdd.ylz.modules.excel.VO.ExcelReadVO;
import com.gdd.ylz.modules.excel.listener.CommenListener;
import com.gdd.ylz.modules.excel.listener.ContactListener;
import com.gdd.ylz.modules.excel.util.ExcelUtils;
import com.gdd.ylz.modules.mail.entity.Contact;
import com.gdd.ylz.modules.mail.service.IContactService;
import com.gdd.ylz.modules.sys.entity.TSysUser;
import com.gdd.ylz.modules.sys.service.ITSysUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：weimin
 * @date ：Created in 2022/3/10 0010 11:07
 * @description：测试Excel工具类
 * @modified By：`
 * @version: 1.0
 */
public class TestEasyExcelUtils extends YlzApplicationTests {
    @Autowired
    private IContactService contactService;
    @Autowired
    private ITSysUserService sysUserService;
    @Autowired
    private HorizontalCellStyleStrategy horizontalCellStyleStrategy;

    /**
     *
     * 测试简单读一个sheet
     *
     *
     */
    @Test
    public void testSimpleRead(){
        ExcelReadVO<Contact> excelReadVO=new ExcelReadVO<>();
        //new 一个CommenListener,也可自定义个Listener 继承CommenListener 重写其中的方法
//        CommenListener<Contact> commenListener=new CommenListener<>(contactService);
//        excelReadVO.setClazz(Contact.class);
//        excelReadVO.setCommenListener(commenListener);
//       自定义listener
        ContactListener contactListener=new ContactListener(contactService);
        excelReadVO.setClazz(Contact.class);
        excelReadVO.setCommenListener(contactListener);
        ExcelUtils.simpleRead("C:\\Users\\Administrator\\Desktop\\contact.xlsx",excelReadVO);
    }
   /**
    * @author weimin
    * @date 2022/3/10 0010 15:04
    * @param []
    * @return void
    * @Version1.0
    */
   @Test
   public void testSimpleReadRepeated(){
       ExcelReadVO<Contact> excelReadVO=new ExcelReadVO<>();
       //new 一个CommenListener,也可自定义个Listener 继承CommenListener 重写其中的方法
//        CommenListener<Contact> commenListener=new CommenListener<>(contactService);
//        excelReadVO.setClazz(Contact.class);
//        excelReadVO.setCommenListener(commenListener);
//       自定义listener
       ContactListener contactListener=new ContactListener(contactService);
       excelReadVO.setClazz(Contact.class);
       excelReadVO.setCommenListener(contactListener);
       ExcelReadVO<TSysUser> excelReadVO2=new ExcelReadVO<>();
       //new 一个CommenListener,也可自定义个Listener 继承CommenListener 重写其中的方法
//        CommenListener<Contact> commenListener=new CommenListener<>(contactService);
//        excelReadVO.setClazz(Contact.class);
//        excelReadVO.setCommenListener(commenListener);
//       自定义listener
       CommenListener<TSysUser> sysUserCommenListener=new CommenListener(sysUserService);
       excelReadVO2.setClazz(TSysUser.class);
       excelReadVO2.setCommenListener(sysUserCommenListener);
       Map<Integer,ExcelReadVO> excelReadVOMap=new HashMap<>();
       excelReadVOMap.put(0,excelReadVO);
       excelReadVOMap.put(1,excelReadVO2);
       ExcelUtils.simpleReadRepeated("C:\\Users\\Administrator\\Desktop\\contact.xlsx",excelReadVOMap);
   }

    @Test
    public void simpleReadAll(){
        ExcelReadVO<Contact> excelReadVO=new ExcelReadVO<>();
        ContactListener contactListener=new ContactListener(contactService);
        excelReadVO.setClazz(Contact.class);
        excelReadVO.setCommenListener(contactListener);
        ExcelUtils.simpleReadAll("C:\\Users\\Administrator\\Desktop\\contact.xlsx",excelReadVO);
   }

   @Test
    public void simpleWrite(){
       List<Contact> contactList= contactService.list();
       String fileName="C:\\Users\\Administrator\\Desktop\\contact.xlsx";
       // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
       EasyExcel.write(fileName, Contact.class).inMemory(true).registerWriteHandler(horizontalCellStyleStrategy).registerWriteHandler(new ContactCellWriteHandler()).registerWriteHandler(new ContactSheetWriteHandler()).sheet("模板")
               .doWrite(contactList);
   }

}

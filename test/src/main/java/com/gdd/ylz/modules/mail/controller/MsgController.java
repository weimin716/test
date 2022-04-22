package com.gdd.ylz.modules.mail.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.modules.mail.dto.EmailMsgDTO;
import com.gdd.ylz.modules.mail.dto.EmailMsgReadDTO;
import com.gdd.ylz.modules.mail.entity.Msg;
import com.gdd.ylz.modules.mail.service.IMsgService;
import com.gdd.ylz.modules.mail.dto.MsgQueryDTO;
import com.gdd.ylz.modules.sys.entity.TPlattUser;
import com.gdd.ylz.modules.sys.service.ITPlattUserService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 邮件信息 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/sys/mail/msg")
@Api(tags={"sys"})
public class MsgController extends UserController {
    @Autowired
    private IMsgService msgService;
    @Autowired
    private ITPlattUserService plattUserService;

    @ApiOperation(value="发送邮件",notes = "发送邮件")
    @PostMapping("/sendemail")
    public DataResult sendEmail(@RequestBody EmailMsgDTO emailMsgDTO){
        TPlattUser plattUser = plattUserService.getById(getUserId());
        return msgService.sendHtmlMail(emailMsgDTO,plattUser);
    }

    @ApiOperation(value="已发送邮件总数",notes = "已发送邮件总数")
    @GetMapping("/alreadysendcount")
    public DataResult getAlreadySendCount(){
        return msgService.getAlreadySendCount(getUserId());
    }

    @ApiOperation(value="已发送邮件列表",notes = "已发送邮件列表")
    @GetMapping("/sendemaillist")
    public DataResult getSendEmailList(MsgQueryDTO msgQueryDTO){
        return msgService.getSendEmailList(msgQueryDTO,getUserId());
    }

    @ApiOperation(value="收件列表",notes = "收件列表")
    @GetMapping("/recieveemaillist")
    public DataResult getRecieveEmailList(MsgQueryDTO msgQueryDTO){
        TPlattUser plattUser = plattUserService.getById(getUserId());
        return msgService.getRecieveEmailList(msgQueryDTO,plattUser);
    }

    @ApiOperation(value="收件邮件列表",notes = "收件邮件列表")
    @GetMapping("/getreciveemail")
    public DataResult getReciveemail(MsgQueryDTO msgQueryDTO){
        TPlattUser plattUser = plattUserService.getById(getUserId());
        return msgService.getHtmlMails(plattUser);
    }

    @ApiOperation(value="更新邮件",notes = "更新邮件")
    @PostMapping("/update")
    public DataResult getSendEmailList(@RequestBody EmailMsgDTO emailMsgDTO){
        Msg msg=new Msg();
        BeanUtils.copyProperties(emailMsgDTO,msg);
        msgService.updateById(msg);
        return DataResult.success();
    }
    @ApiOperation(value="已发送邮件列表",notes = "已发送邮件列表")
    @PostMapping("/updateisread")
    public DataResult updateIsread(@RequestBody EmailMsgReadDTO emailMsgDTO){
        if(emailMsgDTO.getStatus()!=null){
            msgService.update(new UpdateWrapper<Msg>().lambda().set(Msg::getStatus,emailMsgDTO.getStatus()).in(Msg::getId,emailMsgDTO.getMailIds()));
        }else{
            msgService.update(new UpdateWrapper<Msg>().lambda().set(Msg::getIsRead,emailMsgDTO.getIsRead()).in(Msg::getId,emailMsgDTO.getMailIds()));
        }
        return DataResult.success();
    }
    @ApiOperation(value="已发送邮件列表",notes = "已发送邮件列表")
    @PostMapping("/markallread")
    public DataResult markAllRead(@RequestBody List<String> ids){
        msgService.update(new UpdateWrapper<Msg>().lambda().set(Msg::getIsRead,0).in(Msg::getId,ids));
        return DataResult.success();
    }
}


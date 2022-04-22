package com.gdd.ylz.modules.mail.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.modules.mail.dto.ContactMoveDTO;
import com.gdd.ylz.modules.mail.dto.ContactQueryDTO;
import com.gdd.ylz.modules.mail.entity.Contact;
import com.gdd.ylz.modules.mail.service.IContactService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 邮件信息 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/sys/mail/contact")
@Api(tags={"sys"})
public class ContactController extends UserController {
    @Autowired
    private IContactService contactService;
/**
 * @Description
 * @Author  weimin
 * @Date   2022/3/28 10:27
 * @Param  
 * @Return      
 *
 */
    @ApiOperation(value="根据分组id查询联系人",notes = "根据分组id查询联系人")
    @GetMapping("/getcontactbygroup")
    public DataResult getContactByGroup(ContactQueryDTO contactQueryDTO){
        Page page=new Page(contactQueryDTO.getStart(),contactQueryDTO.getLength());
        if(!StringUtils.isEmpty(contactQueryDTO.getGroupId())){
            return DataResult.success( contactService.page(page,new QueryWrapper<Contact>().lambda().eq(Contact::getGroupId,contactQueryDTO.getGroupId()).eq(Contact::getiFlag,1)));
        }else{
            return DataResult.success( contactService.page(page,new QueryWrapper<Contact>().lambda().like(Contact::getName,contactQueryDTO.getName()).eq(Contact::getReferUserId,getUserId()).eq(Contact::getiFlag,1)));
        }

    }
    @ApiOperation(value="新增联系人",notes = "新增联系人")
    @PostMapping("/addcontact")
    public DataResult addContact(@RequestBody Contact contact){
        contact.setReferUserId(getUserId());
        contact.setNameEmail(contact.getName()+"<"+contact.getEmail()+">");
        contactService.save(contact);
        return DataResult.success();
    }
    @ApiOperation(value="编辑联系人",notes = "编辑联系人")
    @PostMapping("/editcontact")
    public DataResult editContact(@RequestBody Contact contact){
        contactService.updateById(contact);
        return DataResult.success();
    }

    @ApiOperation(value="删除联系人",notes = "删除联系人")
    @DeleteMapping("/deletecontact/{id}")
    public DataResult deleteContact(@PathVariable("id") String id){
        contactService.update(new UpdateWrapper<Contact>().lambda().set(Contact::getiFlag,0).eq(Contact::getId,id));
        return DataResult.success();
    }
    @ApiOperation(value="联系人详情",notes = "联系人详情")
    @GetMapping("/getdetail/{id}")
    public DataResult getDetail(@PathVariable("id") String id){
        return DataResult.success(contactService.getById(id));
    }

    @ApiOperation(value="移动联系人",notes = "移动联系人")
    @PostMapping("/movecontact")
    public DataResult moveContact(@RequestBody ContactMoveDTO contactMoveDTO){
        return DataResult.success(contactService.update(new UpdateWrapper<Contact>().lambda().set(Contact::getGroupId,contactMoveDTO.getGroupId()).in(Contact::getId,contactMoveDTO.getIds())));
    }

    @ApiOperation(value="批量删除联系人",notes = "批量删除联系人")
    @PostMapping("/batchdeletecontact")
    public DataResult batchDeleteContact(@RequestBody ContactMoveDTO contactMoveDTO){
        return DataResult.success(contactService.update(new UpdateWrapper<Contact>().lambda().set(Contact::getiFlag,0).in(Contact::getId,contactMoveDTO.getIds())));
    }

}


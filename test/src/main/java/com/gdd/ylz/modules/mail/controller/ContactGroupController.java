package com.gdd.ylz.modules.mail.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.modules.mail.dto.EmailMsgDTO;
import com.gdd.ylz.modules.mail.entity.Contact;
import com.gdd.ylz.modules.mail.entity.ContactGroup;
import com.gdd.ylz.modules.mail.service.IContactGroupService;
import com.gdd.ylz.modules.sys.entity.TPlattUser;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 联系人分组 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/sys/mail/contact-group")
public class ContactGroupController extends UserController {
    @Autowired
    private IContactGroupService contactGroupService;


    @ApiOperation(value="新增分组",notes = "新增分组")
    @PostMapping("/addcontactgroup")
    public DataResult addContactgroup(@RequestBody ContactGroup contactGroup){
        contactGroup.setReferUserId(getUserId());
       contactGroupService.save(contactGroup);
       return DataResult.success();
    }

    @ApiOperation(value="编辑分组",notes = "编辑分组")
    @PostMapping("/editcontactgroup")
    public DataResult updateContactgroup(@RequestBody ContactGroup contactGroup){
        contactGroupService.updateById(contactGroup);
        return DataResult.success();
    }

    @ApiOperation(value="获取分组列表不分页",notes = "获取分组列表不分页")
    @GetMapping("/getcontactgrouplist")
    public DataResult getContactGroupList(){
        return DataResult.success(contactGroupService.list(new QueryWrapper<ContactGroup>().lambda().eq(ContactGroup::getReferUserId,getUserId())));
    }
    @ApiOperation(value="删除分组",notes = "删除分组")
    @DeleteMapping("/deletegroup/{id}")
    public DataResult deleteContact(@PathVariable("id") String id){
        contactGroupService.removeById(id);
        return DataResult.success();
    }
    @ApiOperation(value="获取分组详情",notes = "获取分组详情")
    @GetMapping("/getgroupdetail/{id}")
    public DataResult getGroupDetail(@PathVariable("id") String id){
        return DataResult.success(contactGroupService.getById(id));
    }

    @ApiOperation(value="获取树形联系人数据",notes = "获取树形联系人数据")
    @GetMapping("/getcontacttree")
    public DataResult getContactTree(){
        return contactGroupService.getContactTree(getUserId());
    }

}


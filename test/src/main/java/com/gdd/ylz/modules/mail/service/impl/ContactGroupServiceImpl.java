package com.gdd.ylz.modules.mail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.modules.mail.dto.LayuiDtreeDTO;
import com.gdd.ylz.modules.mail.entity.Contact;
import com.gdd.ylz.modules.mail.entity.ContactGroup;
import com.gdd.ylz.modules.mail.dao.ContactGroupMapper;
import com.gdd.ylz.modules.mail.service.IContactGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdd.ylz.modules.mail.service.IContactService;
import com.gdd.ylz.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 联系人分组 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2021-12-22
 */
@Service
public class ContactGroupServiceImpl extends ServiceImpl<ContactGroupMapper, ContactGroup> implements IContactGroupService {
   @Autowired
   private IContactService contactService;


    @Override
    public DataResult getContactTree(String userId) {
        List<ContactGroup> list = this.list(new QueryWrapper<ContactGroup>().lambda().eq(ContactGroup::getReferUserId, userId));
        List<LayuiDtreeDTO> layuiDtreeDTOList=new ArrayList<>();
        List<Contact> contactList = contactService.list(new QueryWrapper<Contact>().lambda().eq(Contact::getReferUserId, userId).eq(Contact::getiFlag, 1));
        for(ContactGroup contactGroup:list){
            LayuiDtreeDTO layuiDtreeDTO=new LayuiDtreeDTO();
            layuiDtreeDTO.setId(contactGroup.getId());
            layuiDtreeDTO.setTitle(contactGroup.getName());
            List<LayuiDtreeDTO> contactchildren = getContactchildren(contactGroup.getId(), contactList);
            layuiDtreeDTO.setChildren(contactchildren);
            layuiDtreeDTOList.add(layuiDtreeDTO);

        }
        return DataResult.success(layuiDtreeDTOList);
    }

  List<LayuiDtreeDTO>  getContactchildren(String groupId,List<Contact> contactList){
      List<LayuiDtreeDTO> layuiDtreeDTOListContact=new ArrayList<>();
      List<Contact> contactListBygroup = contactList.stream().filter(o -> o.getGroupId().equals(groupId)).collect(Collectors.toList());
      for(Contact contact:contactListBygroup){
          LayuiDtreeDTO layuiDtreeDTO=new LayuiDtreeDTO();
          layuiDtreeDTO.setTitle(contact.getName());
          layuiDtreeDTO.setId(contact.getEmail());
          layuiDtreeDTO.setField(contact.getNameEmail());
          layuiDtreeDTOListContact.add(layuiDtreeDTO);
      }
        return layuiDtreeDTOListContact;

  }
}

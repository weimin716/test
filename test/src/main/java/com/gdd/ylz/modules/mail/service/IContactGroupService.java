package com.gdd.ylz.modules.mail.service;

import com.gdd.ylz.modules.mail.dto.LayuiDtreeDTO;
import com.gdd.ylz.modules.mail.entity.ContactGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.result.DataResult;

import java.util.List;

/**
 * <p>
 * 联系人分组 服务类
 * </p>
 *
 * @author xzg
 * @since 2021-12-22
 */
public interface IContactGroupService extends IService<ContactGroup> {

    DataResult getContactTree(String userId);
}

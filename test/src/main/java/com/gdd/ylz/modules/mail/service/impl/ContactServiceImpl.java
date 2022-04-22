package com.gdd.ylz.modules.mail.service.impl;

import com.gdd.ylz.modules.mail.entity.Contact;
import com.gdd.ylz.modules.mail.dao.ContactMapper;
import com.gdd.ylz.modules.mail.service.IContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邮件信息 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2021-12-22
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements IContactService {

}

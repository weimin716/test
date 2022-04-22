package com.gdd.ylz.modules.excel.listener;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.modules.mail.entity.Contact;

/**
 * @author ：weimin
 * @date ：Created in 2022/3/10 0010 14:23
 * @description：联系人excel监听器
 * @modified By：`
 * @version: 1.0
 */
public class ContactListener extends CommenListener<Contact> {
    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param service
     */
    public ContactListener(IService<Contact> service) {
        super(service);
    }

    @Override
    public void saveData() {
        for(Contact contact:list)
        {
           contact.setiFlag(0);
           contact.setPhone("17458965476");
        }
        service.saveOrUpdateBatch(list);
    }
}

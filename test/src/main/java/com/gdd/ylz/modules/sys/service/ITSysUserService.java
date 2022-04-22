package com.gdd.ylz.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdd.ylz.modules.sys.dto.TSysUserQueryDTO;
import com.gdd.ylz.modules.sys.dto.TSysUserUpdateDTO;
import com.gdd.ylz.modules.sys.entity.TSysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.result.DataResult;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author xzg
 * @since 2021-10-10
 */
public interface ITSysUserService extends IService<TSysUser> {

    IPage<TSysUser> getList(TSysUserQueryDTO tSysUserQueryDTO);

    DataResult checkUpdateSysUserData(TSysUserUpdateDTO tSyUser);

    boolean checkUserDeleteable(String id);

    DataResult getDetail(String id);
}

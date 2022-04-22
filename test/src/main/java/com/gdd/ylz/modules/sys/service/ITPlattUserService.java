package com.gdd.ylz.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdd.ylz.modules.sys.dto.TSysUserQueryDTO;
import com.gdd.ylz.modules.sys.dto.TSysUserUpdateDTO;
import com.gdd.ylz.modules.sys.entity.TPlattUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.modules.sys.entity.TSysUser;
import com.gdd.ylz.result.DataResult;

/**
 * <p>
 * 平台用户表 服务类
 * </p>
 *
 * @author xzg
 * @since 2021-12-10
 */
public interface ITPlattUserService extends IService<TPlattUser> {
    IPage<TPlattUser> getList(TSysUserQueryDTO tSysUserQueryDTO);

    DataResult checkUpdateplattUserData(TSysUserUpdateDTO tSyUser);

    boolean checkUserDeleteable(String id);

    DataResult getDetail(String id);

}

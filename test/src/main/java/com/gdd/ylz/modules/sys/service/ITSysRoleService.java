package com.gdd.ylz.modules.sys.service;

import com.gdd.ylz.modules.sys.dto.RoleAddDTO;
import com.gdd.ylz.modules.sys.dto.RoleQueryDTO;
import com.gdd.ylz.modules.sys.entity.TSysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.result.DataResult;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author xzg
 * @since 2021-10-13
 */
public interface ITSysRoleService extends IService<TSysRole> {

    DataResult getRoleList(RoleQueryDTO roleQueryDTO);

    DataResult addRole(RoleAddDTO roleAddDTO);

    void checkRoleDeleteable(String id);

    DataResult removeRole(String id);

    DataResult updateSysRole(RoleAddDTO roleAddDTO);

    DataResult getRoleDetail(String id);
}

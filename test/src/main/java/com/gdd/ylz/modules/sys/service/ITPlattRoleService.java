package com.gdd.ylz.modules.sys.service;

import com.gdd.ylz.modules.sys.dto.RoleAddDTO;
import com.gdd.ylz.modules.sys.dto.RoleQueryDTO;
import com.gdd.ylz.modules.sys.entity.TPlattRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.result.DataResult;

/**
 * <p>
 * 平台角色表 服务类
 * </p>
 *
 * @author xzg
 * @since 2021-12-10
 */
public interface ITPlattRoleService extends IService<TPlattRole> {
    DataResult getRoleList(RoleQueryDTO roleQueryDTO);

    DataResult addRole(RoleAddDTO roleAddDTO);

    void checkRoleDeleteable(String id);

    DataResult removeRole(String id);

    DataResult updatePlattRole(RoleAddDTO roleAddDTO);

    DataResult getRoleDetail(String id);


}

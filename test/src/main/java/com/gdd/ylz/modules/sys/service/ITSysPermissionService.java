package com.gdd.ylz.modules.sys.service;

import com.gdd.ylz.modules.sys.dto.PermissionQueryDTO;
import com.gdd.ylz.modules.sys.entity.TSysPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.result.DataResult;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author xzg
 * @since 2021-10-13
 */
public interface ITSysPermissionService extends IService<TSysPermission> {

    DataResult getPermissions(String userId);

    DataResult getMenu(String userId);

    DataResult getPermissionsViewTree();

    DataResult getPerList(PermissionQueryDTO permissionQueryDTO);

    DataResult deletePerandSonper(String id);

    DataResult getPerDetail(String id);

    DataResult getFirstPermission();

    DataResult setPermissionOrd(List<TSysPermission> sysPermissionList);
}

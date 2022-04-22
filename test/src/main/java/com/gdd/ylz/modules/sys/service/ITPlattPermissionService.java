package com.gdd.ylz.modules.sys.service;

import com.gdd.ylz.modules.sys.dto.PermissionQueryDTO;
import com.gdd.ylz.modules.sys.entity.TPlattPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.result.DataResult;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author xzg
 * @since 2021-12-10
 */
public interface ITPlattPermissionService extends IService<TPlattPermission> {

    DataResult getPermissions(String userId);

    DataResult getPerList(PermissionQueryDTO permissionQueryDTO);

    DataResult deletePerandSonper(String id);

    DataResult getPerDetail(String id);

    DataResult getPermissionsViewTree();

    DataResult getMenu(String userId);

    DataResult setPermissionOrd(List<TPlattPermission> plattPermissionList);
}

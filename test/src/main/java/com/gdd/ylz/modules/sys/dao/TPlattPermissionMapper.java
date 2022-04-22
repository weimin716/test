package com.gdd.ylz.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.modules.sys.dto.PermissionQueryDTO;
import com.gdd.ylz.modules.sys.dto.PermissionReDTO;
import com.gdd.ylz.modules.sys.entity.TPlattPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author xzg
 * @since 2021-12-10
 */
public interface TPlattPermissionMapper extends BaseMapper<TPlattPermission> {

    IPage<PermissionReDTO> getPerList(@Param("permission")PermissionQueryDTO permissionQueryDTO, Page page);
    PermissionReDTO getPerDetail(@Param("id") String id);

}

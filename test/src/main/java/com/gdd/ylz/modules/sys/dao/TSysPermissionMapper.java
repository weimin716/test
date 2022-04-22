package com.gdd.ylz.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.modules.sys.dto.PermissionQueryDTO;
import com.gdd.ylz.modules.sys.dto.PermissionReDTO;
import com.gdd.ylz.modules.sys.entity.TSysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdd.ylz.result.DataResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author xzg
 * @since 2021-10-13
 */
public interface TSysPermissionMapper extends BaseMapper<TSysPermission> {

    IPage<PermissionReDTO> getPerList(@Param("permission")PermissionQueryDTO permissionQueryDTO,Page page);

    PermissionReDTO getPerDetail(@Param("id") String id);
}

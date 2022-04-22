package com.gdd.ylz.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.modules.sys.dto.RoleQueryDTO;
import com.gdd.ylz.modules.sys.dto.RoleReDTO;
import com.gdd.ylz.modules.sys.entity.TPlattRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 平台角色表 Mapper 接口
 * </p>
 *
 * @author xzg
 * @since 2021-12-10
 */
public interface TPlattRoleMapper extends BaseMapper<TPlattRole> {
    IPage<RoleReDTO> getRoleList(Page page, @Param("role") RoleQueryDTO roleQueryDTO);

    RoleReDTO getRoleDetail(@Param("id") String id);
}

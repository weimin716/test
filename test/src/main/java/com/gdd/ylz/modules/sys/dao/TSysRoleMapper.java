package com.gdd.ylz.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.modules.sys.dto.RoleQueryDTO;
import com.gdd.ylz.modules.sys.dto.RoleReDTO;
import com.gdd.ylz.modules.sys.entity.TSysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author xzg
 * @since 2021-10-13
 */
public interface TSysRoleMapper extends BaseMapper<TSysRole> {

    IPage<RoleReDTO> getRoleList(Page page, @Param("role") RoleQueryDTO roleQueryDTO);

    RoleReDTO getRoleDetail(@Param("id") String id);
}

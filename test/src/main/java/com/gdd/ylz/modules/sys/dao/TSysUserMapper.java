package com.gdd.ylz.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.modules.sys.dto.TSysUserQueryDTO;
import com.gdd.ylz.modules.sys.entity.TSysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author xzg
 * @since 2021-10-10
 */
public interface TSysUserMapper extends BaseMapper<TSysUser> {

    IPage<TSysUser> getList(Page page, @Param("sysuser") TSysUserQueryDTO tSysUserQueryDTO);
}

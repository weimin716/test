package com.gdd.ylz.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.modules.sys.dto.TSysUserQueryDTO;
import com.gdd.ylz.modules.sys.entity.TPlattUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdd.ylz.modules.sys.entity.TSysUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 平台用户表 Mapper 接口
 * </p>
 *
 * @author xzg
 * @since 2021-12-10
 */
public interface TPlattUserMapper extends BaseMapper<TPlattUser> {
    IPage<TPlattUser> getList(Page page, @Param("plattuser") TSysUserQueryDTO tSysUserQueryDTO);

}

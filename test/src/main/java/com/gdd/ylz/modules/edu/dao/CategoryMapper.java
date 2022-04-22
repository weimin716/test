package com.gdd.ylz.modules.edu.dao;

import com.gdd.ylz.modules.edu.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程分类表 Mapper 接口
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
public interface CategoryMapper extends BaseMapper<Category> {

    int insertCategory(@Param("category") Category category);
}

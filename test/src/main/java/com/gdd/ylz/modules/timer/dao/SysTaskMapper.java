package com.gdd.ylz.modules.timer.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.modules.timer.dto.SysTaskQueryDTO;
import com.gdd.ylz.modules.timer.entity.SysTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 定时任务信息表 Mapper 接口
 * </p>
 *
 * @author xzg
 * @since 2022-04-01
 */
public interface SysTaskMapper extends BaseMapper<SysTask> {

    IPage<SysTask> getListByPage(Page page, @Param("sysTask") SysTaskQueryDTO sysTaskQueryDTO);

    int checkExistJobName(@Param("task") SysTask task);

    int checkExistBeanName(@Param("task") SysTask task);
}

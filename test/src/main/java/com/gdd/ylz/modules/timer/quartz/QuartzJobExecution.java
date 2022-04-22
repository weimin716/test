package com.gdd.ylz.modules.timer.quartz;

import com.gdd.ylz.modules.timer.entity.SysTask;
import org.quartz.JobExecutionContext;

public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysTask task) throws Exception {
        Class clz= Class.forName(task.getBeanName());
        clz.getMethod(task.getMethodName()).invoke(clz.newInstance(),null);
    }
}

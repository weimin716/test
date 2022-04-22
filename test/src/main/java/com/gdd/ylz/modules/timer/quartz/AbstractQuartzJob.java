package com.gdd.ylz.modules.timer.quartz;

import com.gdd.ylz.modules.timer.constant.TaskConstants;
import com.gdd.ylz.modules.timer.entity.SysTask;
import com.gdd.ylz.modules.timer.entity.SysTaskLog;
import com.gdd.ylz.modules.timer.service.ISysTaskLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.util.Date;
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public abstract class AbstractQuartzJob implements Job {



    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SysTask task = (SysTask)context.getMergedJobDataMap().get(TaskConstants.TASK_PROPERTIES);
        try {
            before(context, task);
            doExecute(context, task);
            after(context, task, null);
        } catch (Exception ex) {
            log.error("定时任务执行异常：{}", ex.getMessage());
            after(context, task, ex);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param task 任务
     */
    protected void before(JobExecutionContext context, SysTask task) {
        threadLocal.set(new Date());
    }

    protected void after(JobExecutionContext context, SysTask task, Exception ex) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        SysTaskLog sysTaskLog = new SysTaskLog();
        sysTaskLog.setJobId(task.getId());
        sysTaskLog.setJobGroup(task.getJobGroup());
        sysTaskLog.setJobName(task.getJobName());
        sysTaskLog.setBeanName(task.getBeanName());
        sysTaskLog.setMethodName(task.getMethodName());
        sysTaskLog.setStartTime(startTime);
        sysTaskLog.setEndTime(new Date());
        long runMs = sysTaskLog.getEndTime().getTime() - sysTaskLog.getStartTime().getTime();
        sysTaskLog.setMessage(sysTaskLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (null != ex) {
            sysTaskLog.setStatus(1);
            sysTaskLog.setExceptionInfo(ex.getMessage());
        } else {
            sysTaskLog.setStatus(0);
        }
        BeansUtils.getBean(ISysTaskLogService.class).save(sysTaskLog);
    }

    /**
     * 执行方法
     *
     * @param context 工作执行上下文对象
     * @param task 系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void  doExecute(JobExecutionContext context, SysTask task) throws Exception;
}

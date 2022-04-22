package com.gdd.ylz.modules.timer.quartz;

import com.gdd.ylz.modules.timer.service.ISysTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ISysTaskService sysTaskService;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        sysTaskService.initSchedule();
    }
}

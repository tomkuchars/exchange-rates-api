package com.app.exchangerates.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TaskScheduler {

    private final ThreadPoolTaskScheduler taskScheduler;

    private final CronTrigger cronTrigger;

    private final LoadRates loadRates;

    @Autowired
    public TaskScheduler(ThreadPoolTaskScheduler taskScheduler, CronTrigger cronTrigger, LoadRates loadRates) {
        this.taskScheduler = taskScheduler;
        this.cronTrigger = cronTrigger;
        this.loadRates = loadRates;
    }

    @PostConstruct
    public void scheduleRunnableWithCronTrigger() {
        taskScheduler.schedule(new LoadRatesRunnable(loadRates), cronTrigger);
    }

}
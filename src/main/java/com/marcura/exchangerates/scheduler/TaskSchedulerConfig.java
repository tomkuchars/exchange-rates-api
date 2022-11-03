package com.marcura.exchangerates.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.time.ZoneId;

@Configuration
@ComponentScan(basePackages = "com.marcura.exchangerates.scheduler", basePackageClasses = {TaskScheduler.class})
public class TaskSchedulerConfig {

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("TaskScheduler");
        return threadPoolTaskScheduler;
    }

    @Bean
    public CronTrigger cronTrigger() {
        return new CronTrigger("0 5 0 * * ?", ZoneId.of("UTC")); //At 00:05:00am every day
    }

}
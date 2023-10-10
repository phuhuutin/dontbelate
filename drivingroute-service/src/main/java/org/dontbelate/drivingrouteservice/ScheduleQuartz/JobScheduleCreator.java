package org.dontbelate.drivingrouteservice.ScheduleQuartz;

import org.dontbelate.drivingrouteservice.service.DrivingRouteService;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
@Component
public class JobScheduleCreator {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleCreator.class);
    public JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable,
                               ApplicationContext context, String jobName, String jobGroup) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(isDurable);
        factoryBean.setApplicationContext(context);
        factoryBean.setName(jobName);
        factoryBean.setGroup(jobGroup);

        // set job data map
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("routeName", jobName);
        jobDataMap.put("userID", jobGroup);

        factoryBean.setJobDataMap(jobDataMap);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }
    public CronTrigger createCronTrigger(String triggerName, Date startTime, String cronExpression, int misFireInstruction) {

        CronTrigger trigger = newTrigger()
                .startNow()
                .withSchedule(cronSchedule(cronExpression)) // fire every wednesday at 15:00
                .build();
        return trigger;
    }
}

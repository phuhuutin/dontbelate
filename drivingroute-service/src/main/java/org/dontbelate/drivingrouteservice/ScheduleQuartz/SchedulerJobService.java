package org.dontbelate.drivingrouteservice.ScheduleQuartz;

import org.dontbelate.drivingrouteservice.exception.ResourceAlreadyExistException;
import org.dontbelate.drivingrouteservice.repository.DBLDrivingRouteRepository;
import org.dontbelate.drivingrouteservice.service.DrivingRouteService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.TimeZone;

import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.TriggerBuilder.newTrigger;

@Transactional
@Service
public class SchedulerJobService {
    private static final Logger logger = LoggerFactory.getLogger(DrivingRouteService.class);
//    @Autowired
//    private Scheduler scheduler;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private DBLDrivingRouteRepository dblDrivingRouteRepository;
    @Autowired
    private ApplicationContext context;

    @Autowired
    private JobScheduleCreator jobScheduleCreator;
    @SuppressWarnings("unchecked")
    public void save(RouteJobInfor jobInfo){
    try {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobDetail jobDetail = JobBuilder
                .newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
                .withIdentity(jobInfo.getRouteName(), jobInfo.getUserID()).build();
        if (!scheduler.checkExists(jobDetail.getKey())) {
            jobDetail = jobScheduleCreator.createJob(
                    (Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()), false, context,
                    jobInfo.getRouteName(), jobInfo.getUserID(), jobInfo.getRouteID().toString(), jobInfo.getStartTime(), jobInfo.getExpectedDuration());
            LocalTime fifteenMinssearly = jobInfo.getStartTime().minusMinutes(15);

            Integer[] dayOfWeekArray = jobInfo.getDaysOfWeek().stream()
                    .map(e -> e.getValue() + 1)
                    .toArray(Integer[]::new);
            Trigger trigger = newTrigger()
                    .startNow()
                    .withIdentity(jobInfo.getRouteName(), jobInfo.getUserID())
                    .withSchedule(atHourAndMinuteOnGivenDaysOfWeek(fifteenMinssearly.getHour(), fifteenMinssearly.getMinute(), dayOfWeekArray))
                    .forJob(jobDetail)
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("Successfully Added {}|{} Route[{}] to the scheduler !",
                    jobInfo.getRouteName(),
                    jobInfo.getUserID(),
                    fifteenMinssearly +"|"+jobInfo.getDaysOfWeek().toString());
        } else {
            throw new ResourceAlreadyExistException("RouteJobInfor", "routeName|userID", jobInfo.getRouteName() + "|" + jobInfo.getUserID());
        }
    }catch (ClassNotFoundException e){
        throw new RuntimeException("No Job Class Found");
    } catch (SchedulerException e){
        throw new RuntimeException(e.getMessage());
    }
    }
    @SuppressWarnings("unchecked")
    public void update(RouteJobInfor jobInfo){
        try{
            JobDetail jobDetail = jobScheduleCreator.createJob(
                    (Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()), false, context,
                    jobInfo.getRouteName(), jobInfo.getUserID(),jobInfo.getRouteID().toString(),jobInfo.getStartTime(),jobInfo.getExpectedDuration());
            LocalTime fifteenMinssearly = jobInfo.getStartTime().minusMinutes(15);
            Integer[] dayOfWeekArray = jobInfo.getDaysOfWeek().stream()
                    .map(e -> e.getValue() + 1)
                    .toArray(Integer[]::new);
            Trigger trigger = newTrigger()
                    .startNow()
                    .withIdentity(jobInfo.getRouteName(), jobInfo.getUserID())
                    .withSchedule(atHourAndMinuteOnGivenDaysOfWeek(fifteenMinssearly.getHour(), fifteenMinssearly.getMinute(), dayOfWeekArray))
                    .forJob(jobDetail)
                    .build();
            schedulerFactoryBean.getScheduler().rescheduleJob(trigger.getKey(), trigger);
            logger.info("Successfully Updated {}|{} Route[{}] to the scheduler !",
                    jobInfo.getRouteName(),
                    jobInfo.getUserID(),
                    fifteenMinssearly +"|"+jobInfo.getDaysOfWeek().toString());
        }catch (ClassNotFoundException e){
            throw new RuntimeException("No Job Class Found");
        } catch (SchedulerException e){
            throw new RuntimeException(e.getMessage());
        }


    }
}

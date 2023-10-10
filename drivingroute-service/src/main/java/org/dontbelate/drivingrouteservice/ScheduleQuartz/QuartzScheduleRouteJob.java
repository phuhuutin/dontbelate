package org.dontbelate.drivingrouteservice.ScheduleQuartz;

import org.dontbelate.drivingrouteservice.service.DrivingRouteService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.stream.IntStream;

public class QuartzScheduleRouteJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(QuartzScheduleRouteJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("Loading Info From Google Map API................({})",context.getMergedJobDataMap().getString("routeName"));
        IntStream.range(0, 5).forEach(i -> {
            logger.info("Counting - {}", i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        });
        logger.info("End................");
    }

}

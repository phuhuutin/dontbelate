package org.dontbelate.drivingrouteservice.ScheduleQuartz;

import org.dontbelate.drivingrouteservice.EmailSender.EmailServiceImpl;
import org.dontbelate.drivingrouteservice.EmailSender.RouteStatusEmailMessage;
import org.dontbelate.drivingrouteservice.dto.GoogleDistanceMatrixResponse;
import org.dontbelate.drivingrouteservice.exception.ResourceNotFoundException;
import org.dontbelate.drivingrouteservice.service.TravelTimeService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;
@Component
public class GoogleCheckingQuartzJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(GoogleCheckingQuartzJob.class);
    @Autowired
    private TravelTimeService travelTimeService;

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("Loading Info From Google Map API................({})",context.getMergedJobDataMap().getString("routeName"));
        ResponseEntity<GoogleDistanceMatrixResponse> result = travelTimeService.getRouteTime(Long.valueOf(context.getMergedJobDataMap().getString("routeID")));
        if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null){
            RouteStatusEmailMessage newMailMess = new RouteStatusEmailMessage();
            newMailMess.setRouteName(context.getMergedJobDataMap().getString("routeName"));
            newMailMess.setUserID(Long.valueOf(context.getMergedJobDataMap().getString("userID")));
            newMailMess.setStartTime(context.getMergedJobDataMap().getString("startTime"));
            newMailMess.setDuration(result.getBody().getDuration()/60);
            newMailMess.setStartAddress(result.getBody().getOriginAddresses().get(0));
            newMailMess.setEndAddress(result.getBody().getDestinationAddresses().get(0));
            newMailMess.setStatus(context.getMergedJobDataMap().getInt("expectedDuration"));
            emailService.sendRouteStatus(newMailMess);
        }else {
            throw new ResourceNotFoundException("GoogleDistanceMatrixResponse", "routeID",Long.valueOf(context.getMergedJobDataMap().getString("routeID")));
        }



        logger.info("End................");
    }

}

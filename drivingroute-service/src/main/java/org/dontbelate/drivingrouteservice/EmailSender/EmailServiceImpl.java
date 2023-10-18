package org.dontbelate.drivingrouteservice.EmailSender;

import org.dontbelate.drivingrouteservice.dto.DBLUser;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;
import org.dontbelate.drivingrouteservice.exception.ResourceNotFoundException;
import org.dontbelate.drivingrouteservice.service.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public void sendAddedScheduleMessage(DBLDrivingRoute route) {
        DBLUser theUser = userServiceClient.getUserById(route.getUserID()).getBody();


        if(theUser == null ){
            throw new ResourceNotFoundException("DBLUser","userID",route.getUserID());
        } else if(theUser.getEmail().isEmpty()) {
            throw new ResourceNotFoundException("DBLUser","Email",route.getUserID());
        } else {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(theUser.getEmail());
            message.setSubject("DontBeLate: You Successfully scheduled a route!");
            message.setText(
                    String.format(
                            "Hi %s, \n" +
                                    "You successfully scheduled a route named %s in our system \n" +
                                    "From: %s \n" +
                                    "To: %s \n" +
                                    "Start Time: %s on %s \n" +
                                    "We will check and send you the traffic status 15 minutes before your start time \n"+
                            "Thank \n" +
                            "Dont Be Late!"
                            ,theUser.getUsername()
                            ,route.getRouteName()
                            ,route.getStartLocation().getReadableAddress()
                            ,route.getEndLocation().getReadableAddress()
                            ,route.getStartTime()
                            ,route.getDaysOfWeek().toString()
                    )
            );
            emailSender.send(message);
        }
    }

    @Override
    public void sendRouteStatus(RouteStatusEmailMessage routeStatusEmailMessage) {
        DBLUser theUser = userServiceClient.getUserById(routeStatusEmailMessage.getUserID()).getBody();


        if(theUser == null ){
            throw new ResourceNotFoundException("DBLUser","userID",routeStatusEmailMessage.getUserID());
        } else if(theUser.getEmail().isEmpty()) {
            throw new ResourceNotFoundException("DBLUser","Email",routeStatusEmailMessage.getUserID());
        } else {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(theUser.getEmail());
            message.setSubject(String.format("DontBeLate: %s Route Status", routeStatusEmailMessage.getRouteName() ));
            message.setText(
                    String.format(
                            "Hi %s, \n" +
                                    "This is your %s route status: \n" +
                                    "From: %s \n" +
                                    "To: %s \n" +
                                    "Start Time: %s \n" +
                                    "Status: %s \n"+
                                    "Duration: %s minutes\n"+
                                    "Thank \n" +
                                    "Dont Be Late!"
                            ,theUser.getUsername()
                            ,routeStatusEmailMessage.getRouteName(),
                            routeStatusEmailMessage.getStartAddress(),
                            routeStatusEmailMessage.getEndAddress(),
                            routeStatusEmailMessage.getStartTime(),
                            routeStatusEmailMessage.getStatus(),
                            routeStatusEmailMessage.getDuration()

                    )
            );
            emailSender.send(message);
        }




    }


}

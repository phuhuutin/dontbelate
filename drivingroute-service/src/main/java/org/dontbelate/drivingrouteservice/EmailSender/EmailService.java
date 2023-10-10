package org.dontbelate.drivingrouteservice.EmailSender;

import org.dontbelate.drivingrouteservice.dto.RouteStatusEmailMessage;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;

public interface EmailService {
    void sendAddedScheduleMessage(DBLDrivingRoute dblDrivingRoute);
    void sendRouteStatus(RouteStatusEmailMessage routeStatusEmailMessage);
}

package org.dontbelate.drivingrouteservice.ScheduleQuartz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;
@Setter
@Getter
@AllArgsConstructor
public class RouteJobInfor{

    private Long routeID;
    private String routeName;
    private String userID;
    private Set<DayOfWeek> daysOfWeek;

    private LocalTime startTime;

//    private String cronExpression;
    private String jobClass;

    private Integer expectedDuration;
}

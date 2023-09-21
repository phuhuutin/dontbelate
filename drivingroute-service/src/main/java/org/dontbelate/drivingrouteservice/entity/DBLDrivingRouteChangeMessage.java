package org.dontbelate.drivingrouteservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DBLDrivingRouteChangeMessage {
    private String type;
    private String action;
    private String routeId;

    public DBLDrivingRouteChangeMessage(String type, String action, String routeId) {
        this.type = type;
        this.action = action;
        this.routeId = routeId;
    }
}

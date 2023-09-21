package org.dontbelate.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DBLDrivingRouteChangeMessage {
    private String type;
    private String action;
    private Long routeId;

    public DBLDrivingRouteChangeMessage(String type, String action, Long routeId) {
        this.type = type;
        this.action = action;
        this.routeId = routeId;
    }
    @Override
    public String toString() {
        return "DBLDrivingRouteChangeMessage{" +
                "type='" + type + '\'' +
                ", action='" + action + '\'' +
                ", routeId=" + routeId +
                '}';
    }

}

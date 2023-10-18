package org.dontbelate.drivingrouteservice.EmailSender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dontbelate.drivingrouteservice.entity.DBLAddress;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteStatusEmailMessage {
    private Long userID;
    private String routeName;
    private String startAddress;
    private String endAddress;
    private String status;
    private Integer duration;
    private String startTime;

    public void setStatus(Integer expectedDuration){
        if(expectedDuration.compareTo(this.duration) >= 0){
            this.status="The traffic is good compared to your expected duration! you will arrive on time.";
        } else {
            this.status = "The traffic is bad compared to your expected duration! you should start " + (duration-expectedDuration) + " minutes early.";
        }
    }
}

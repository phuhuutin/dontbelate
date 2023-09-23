package org.dontbelate.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dontbelate.userservice.entity.DBLAddress;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.hateoas.RepresentationModel;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("DBLDrivingRoute")
public class DBLDrivingRoute {
    @Id
    private Long id;

    private String routeName;

    private DBLAddress startLocation;

    private DBLAddress endLocation;
    private int expectedDuration;

    private LocalTime startTime;

    private Set<DayOfWeek> daysOfWeek;
    private Long userID;
    @Override
    public String toString() {
        return "DBLDrivingRoute{" +
                "id=" + id +
                ", routeName='" + routeName + '\'' +
                ", startLocation=" + startLocation +
                ", endLocation=" + endLocation +
                ", expectedDuration=" + expectedDuration +
                ", startTime=" + startTime +
                ", daysOfWeek=" + daysOfWeek +
                ", userID=" + userID +
                '}';
    }
}

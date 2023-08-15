package org.dontbelate.userservice.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dontbelate.userservice.entity.DBLAddress;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DBLDrivingRouteDTO {
    private Long id;

    private String routeName;

    private DBLAddress startLocation;

    private DBLAddress endLocation;
    private int expectedDuration;

    private LocalTime startTime;

    private Set<DayOfWeek> daysOfWeek;
    private Long userID;
}

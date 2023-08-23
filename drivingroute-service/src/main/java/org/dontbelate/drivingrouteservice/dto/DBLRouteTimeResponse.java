package org.dontbelate.drivingrouteservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DBLRouteTimeResponse {
    private DBLDrivingRoute route;
    private int realDurationTimeinSec;

    private int durationOffsetinSec;




}

package org.dontbelate.userservice.service;

import org.dontbelate.userservice.dto.DBLDrivingRouteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "drivingroute-service")
public interface  DrivingRouteClient {
    @GetMapping("api/routeservice/hello")
    public String hellofromDrivingRouteService();
    @PostMapping("api/routeservice/add")
    public ResponseEntity<DBLDrivingRouteDTO> saveDrivingRoute(DBLDrivingRouteDTO theRoute);
}

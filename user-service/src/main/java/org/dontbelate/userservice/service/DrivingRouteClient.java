package org.dontbelate.userservice.service;

import org.dontbelate.userservice.dto.DBLDrivingRoute;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "drivingroute-service")
public interface  DrivingRouteClient {
    @GetMapping("api/routeservice/hello")
    public String hellofromDrivingRouteService();
    @PostMapping("api/routeservice/add")
    public ResponseEntity<DBLDrivingRoute> saveDrivingRoute(DBLDrivingRoute theRoute);

    @GetMapping("api/routeservice/get/{id}")
    public ResponseEntity<DBLDrivingRoute> getRoute( @PathVariable Long id);

}

package org.dontbelate.userservice.service;

import feign.Headers;
import org.dontbelate.userservice.dto.DBLDrivingRoute;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "drivingroute-service")
public interface  DrivingRouteClient {
    @GetMapping("api/routeservice/private/hello")
    public String hellofromDrivingRouteService();
    @PostMapping("api/routeservice/private/add")
    public ResponseEntity<DBLDrivingRoute> saveDrivingRoute(DBLDrivingRoute theRoute);

    @GetMapping("api/routeservice/private/hello")
    public String hello();
    @GetMapping("api/routeservice/private/get/{id}")
    public ResponseEntity<DBLDrivingRoute> getRoute( @PathVariable Long id);

}

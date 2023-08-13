package org.dontbelate.userservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "drivingroute-service")
public interface DrivingRouteClient {
    @GetMapping("api/routeservice/hello")
    public String hellofromDrivingRouteService();
}

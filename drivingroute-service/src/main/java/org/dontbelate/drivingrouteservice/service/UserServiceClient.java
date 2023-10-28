package org.dontbelate.drivingrouteservice.service;

import org.dontbelate.drivingrouteservice.dto.DBLUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("api/userservice/private/{userId}")
    public ResponseEntity<DBLUser> getUserById(@PathVariable Long userId);
}

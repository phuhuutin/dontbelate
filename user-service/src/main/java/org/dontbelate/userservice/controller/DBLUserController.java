package org.dontbelate.userservice.controller;

import io.micrometer.observation.annotation.Observed;
import org.dontbelate.userservice.dto.DBLDrivingRoute;
import org.dontbelate.userservice.dto.DBLUserDTO;
import org.dontbelate.userservice.entity.DBLUser;
import org.dontbelate.userservice.repository.RouteRedisRepository;
import org.dontbelate.userservice.service.DrivingRouteClient;
import org.dontbelate.userservice.service.RedisDrivingRouteClientService;
import org.dontbelate.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/userservice")
public class DBLUserController {

    @Autowired
    private RedisDrivingRouteClientService redisDrivingRouteClientService;
    @Autowired
     private UserService userService;

    @Autowired
    private DrivingRouteClient drivingRouteClient;




    @PostMapping("addUser")
    public ResponseEntity<DBLUser> saveUser(@RequestBody DBLUserDTO dblUserDTO){
        DBLUser savedUser = userService.saveUser(dblUserDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @PostMapping("addRoute")
    public ResponseEntity<DBLDrivingRoute> saveRoute(@RequestBody DBLDrivingRoute theRoute){
        return  drivingRouteClient.saveDrivingRoute(theRoute);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<DBLUser> getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @Observed

    @GetMapping("getRoute/{id}")
    public ResponseEntity<DBLDrivingRoute> getRoutebyId(@PathVariable Long id){
        return new ResponseEntity<>(redisDrivingRouteClientService.getDBLDrivingRoute(id),HttpStatus.OK );

    }

}

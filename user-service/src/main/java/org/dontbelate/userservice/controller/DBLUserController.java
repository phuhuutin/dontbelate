package org.dontbelate.userservice.controller;

import org.dontbelate.userservice.dto.DBLDrivingRouteDTO;
import org.dontbelate.userservice.dto.DBLUserDTO;
import org.dontbelate.userservice.entity.DBLUser;
import org.dontbelate.userservice.service.DrivingRouteClient;
import org.dontbelate.userservice.service.LoadDummyData;
import org.dontbelate.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/userservice")
public class DBLUserController {
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
    public ResponseEntity<DBLDrivingRouteDTO> saveRoute(@RequestBody DBLDrivingRouteDTO theRoute){
        return  drivingRouteClient.saveDrivingRoute(theRoute);
    }
}

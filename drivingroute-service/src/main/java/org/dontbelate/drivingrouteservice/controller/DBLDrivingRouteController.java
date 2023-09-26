package org.dontbelate.drivingrouteservice.controller;

import lombok.RequiredArgsConstructor;
import org.dontbelate.drivingrouteservice.dto.GoogleDistanceMatrixResponse;
import org.dontbelate.drivingrouteservice.entity.ActionEnum;
import org.dontbelate.drivingrouteservice.entity.DBLAddress;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRouteChangeMessage;
import org.dontbelate.drivingrouteservice.repository.DBLAddressRepository;
import org.dontbelate.drivingrouteservice.repository.DBLDrivingRouteRepository;
import org.dontbelate.drivingrouteservice.service.DrivingRouteService;
import org.dontbelate.drivingrouteservice.service.TravelTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@RestController
@RequestMapping("api/routeservice")
@RefreshScope
@RequiredArgsConstructor
public class DBLDrivingRouteController {
    @Autowired
    private final StreamBridge streamBridge;
    @Autowired
    private DrivingRouteService drivingRouteService;
    @Autowired
    private DBLAddressRepository dblAddressRepository;
    @Autowired
    private TravelTimeService travelTimeService;

    @PostMapping("add")
    public ResponseEntity<DBLDrivingRoute> saveDBLDrivingRoute(@RequestBody DBLDrivingRoute theRoute){
        return drivingRouteService.saveADrivingRoute(theRoute);
    }


    @GetMapping("testException")
    public void hellofromDrivingRouteService(){
        throw new RuntimeException("Fail to delete Item or send out message");

    }

    @PostMapping("checkAddress")
    public DBLAddress checkAddress(@RequestBody DBLAddress theAddress){
        return dblAddressRepository.findByStreetIgnoreCaseAndCityIgnoreCaseAndStateIgnoreCaseAndZipCodeIgnoreCase(
                theAddress.getStreet(), theAddress.getCity(), theAddress.getState(), theAddress.getZipCode()
        );
    }
    @GetMapping("check/{id}")
    public GoogleDistanceMatrixResponse checkTrafficOfaRoute(@PathVariable Long id){
        return travelTimeService.getRouteTime(id).getBody();
    }


    @GetMapping("get/{id}")
    public ResponseEntity<DBLDrivingRoute> getRoute(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(drivingRouteService.getRoutebyId(id));
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<String> deletebyID(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK.value()).body(drivingRouteService.deletebyRoutebyId(id));
    }


}

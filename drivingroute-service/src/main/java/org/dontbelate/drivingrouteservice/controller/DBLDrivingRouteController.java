package org.dontbelate.drivingrouteservice.controller;

import org.dontbelate.drivingrouteservice.entity.DBLAddress;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;
import org.dontbelate.drivingrouteservice.repository.DBLAddressRepository;
import org.dontbelate.drivingrouteservice.repository.DBLDrivingRouteRepository;
import org.dontbelate.drivingrouteservice.service.DrivingRouteService;
import org.dontbelate.drivingrouteservice.service.LoadDummyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/routeservice")
public class DBLDrivingRouteController {
    @Autowired
    private DrivingRouteService drivingRouteService;
    @Autowired
    private DBLAddressRepository dblAddressRepository;
    @PostMapping("add")
    public ResponseEntity<DBLDrivingRoute> saveDBLDrivingRoute(@RequestBody DBLDrivingRoute theRoute){
        DBLDrivingRoute savedRoute = drivingRouteService.saveADrivingRoute(theRoute);
        return new ResponseEntity<>(savedRoute, HttpStatus.CREATED);
    }

    @GetMapping("hello")
    public String hellofromDrivingRouteService(){

        return "Hello from DrivingRouteService";

    }

    @PostMapping("checkAddress")
    public DBLAddress checkAddress(@RequestBody DBLAddress theAddress){
        return dblAddressRepository.findByStreetIgnoreCaseAndCityIgnoreCaseAndStateIgnoreCaseAndZipCodeIgnoreCase(
                theAddress.getStreet(), theAddress.getCity(), theAddress.getState(), theAddress.getZipCode()
        );
    }

}

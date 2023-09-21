package org.dontbelate.drivingrouteservice.controller;

import org.dontbelate.drivingrouteservice.dto.GoogleDistanceMatrixResponse;
import org.dontbelate.drivingrouteservice.entity.DBLAddress;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;
import org.dontbelate.drivingrouteservice.repository.DBLAddressRepository;
import org.dontbelate.drivingrouteservice.repository.DBLDrivingRouteRepository;
import org.dontbelate.drivingrouteservice.service.DrivingRouteService;
import org.dontbelate.drivingrouteservice.service.LoadDummyData;
import org.dontbelate.drivingrouteservice.service.TravelTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/routeservice")
@RefreshScope
public class DBLDrivingRouteController {
    @Autowired
    private DrivingRouteService drivingRouteService;
    @Autowired
    private DBLAddressRepository dblAddressRepository;
    @Autowired
    private TravelTimeService travelTimeService;

    @Value("${dontbelate.refreshCheck}")
    private String reFresshConfigurationCheck;
    @Autowired
    private DBLDrivingRouteRepository dBLDrivingRouteRepository;

    @GetMapping("refreshCheck")
    public String refreshCheck(){
        return this.reFresshConfigurationCheck;
    }

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
    @GetMapping("check/{id}")
    public GoogleDistanceMatrixResponse checkTrafficOfaRoute(@PathVariable Long id){
        return travelTimeService.getRouteTime(id).getBody();
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<String> deletebyID(@PathVariable Long id){
        try{
            if(dBLDrivingRouteRepository.existsById(id)){
                dBLDrivingRouteRepository.deleteById(id);
                return ResponseEntity.ok("Item deleted successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete item.");

        }

    }


}

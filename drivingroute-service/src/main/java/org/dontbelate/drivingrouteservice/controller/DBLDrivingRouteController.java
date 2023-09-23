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


//    @Value("${dontbelate.refreshCheck}")
//    private String reFresshConfigurationCheck;
    @Autowired
    private DBLDrivingRouteRepository dBLDrivingRouteRepository;
    @Autowired
    private  Function<String, String> sayHello;

//    @GetMapping("refreshCheck")
//    public String refreshCheck(){
//        return this.reFresshConfigurationCheck;
//    }

    @PostMapping("add")
    public ResponseEntity<DBLDrivingRoute> saveDBLDrivingRoute(@RequestBody DBLDrivingRoute theRoute){
        DBLDrivingRoute savedRoute = drivingRouteService.saveADrivingRoute(theRoute);
        return new ResponseEntity<>(savedRoute, HttpStatus.CREATED);
    }

    @GetMapping("hello/{message}")
    public String hellofromDrivingRouteService(@PathVariable String message){
        streamBridge.send("helloTopic",message);
        return message;

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
    public ResponseEntity<DBLDrivingRoute> getRoute(@PathVariable Long id){
        Optional<DBLDrivingRoute> routeOP = dBLDrivingRouteRepository.findById(id);
        if (routeOP.isPresent()) {
            DBLDrivingRoute route = routeOP.get();
            return ResponseEntity.ok(route);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @GetMapping("delete/{id}")
    public ResponseEntity<String> deletebyID(@PathVariable Long id){
        try{
            if(dBLDrivingRouteRepository.existsById(id)){

                dBLDrivingRouteRepository.deleteById(id);
                DBLDrivingRouteChangeMessage newMessage = new DBLDrivingRouteChangeMessage(DBLDrivingRoute.class.getTypeName(),
                        ActionEnum.DELETED.toString(), id);
                streamBridge.send("helloTopic", MessageBuilder.withPayload(newMessage).build());
                return ResponseEntity.ok("Item deleted successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete item.");

        }

    }


}

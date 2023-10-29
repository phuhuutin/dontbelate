package org.dontbelate.drivingrouteservice.controller;

import lombok.RequiredArgsConstructor;
import org.dontbelate.drivingrouteservice.EmailSender.EmailServiceImpl;
import org.dontbelate.drivingrouteservice.ScheduleQuartz.GoogleCheckingQuartzJob;
import org.dontbelate.drivingrouteservice.ScheduleQuartz.RouteJobInfor;
import org.dontbelate.drivingrouteservice.ScheduleQuartz.SchedulerJobService;
import org.dontbelate.drivingrouteservice.dto.DBLUser;
import org.dontbelate.drivingrouteservice.dto.GoogleDistanceMatrixResponse;
import org.dontbelate.drivingrouteservice.entity.DBLAddress;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;
import org.dontbelate.drivingrouteservice.repository.DBLAddressRepository;
import org.dontbelate.drivingrouteservice.service.DrivingRouteService;
import org.dontbelate.drivingrouteservice.service.TravelTimeService;
import org.dontbelate.drivingrouteservice.service.UserServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/routeservice/private")
@RefreshScope
@RequiredArgsConstructor
public class DBLDrivingRouteController {
    private static final Logger logger = LoggerFactory.getLogger(DBLDrivingRouteController.class);

    @Autowired
    private final StreamBridge streamBridge;
    @Autowired
    private DrivingRouteService drivingRouteService;
    @Autowired
    private DBLAddressRepository dblAddressRepository;
    @Autowired
    private TravelTimeService travelTimeService;

    @Autowired
    private SchedulerJobService schedulerJobService;

    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private EmailServiceImpl emailService;
//    @GetMapping("testEmail")
//    public ResponseEntity<String> sendEmail(){
//        String message = "Successfully sent the email";
//        EmailDetails email = new EmailDetails("phuhuutin@gmail.com","hello","hello","hello");
//        emailService.sendSimpleMessage(email);
//
//        return ResponseEntity.status(HttpStatus.OK.value()).body(message);
//    }

    @GetMapping("getUser/{userID}")
    public ResponseEntity<DBLUser> getUserById(@PathVariable Long userID){
        return userServiceClient.getUserById(userID);
    }

    @PostMapping("add")
    public ResponseEntity<DBLDrivingRoute> saveDBLDrivingRoute(@RequestBody DBLDrivingRoute theInputRoute){
        ResponseEntity<DBLDrivingRoute> theRouteResp = drivingRouteService.saveADrivingRoute(theInputRoute);
        DBLDrivingRoute theRoute = theRouteResp.getBody();
        if(theRoute.isStatus()){
            RouteJobInfor theRouteJobInfor = new RouteJobInfor(theRoute.getId(), theRoute.getRouteName(), theRoute.getUserID().toString()
            , theRoute.getDaysOfWeek(), theRoute.getStartTime(), GoogleCheckingQuartzJob.class.getName(),theRoute.getExpectedDuration());
            schedulerJobService.save(theRouteJobInfor);
            emailService.sendAddedScheduleMessage(theRoute);
        }
        return theRouteResp;
    }

    @PostMapping("addJob")
    public ResponseEntity<String> addJob(@RequestBody RouteJobInfor theRoute){
        String message = "Successfully schedule Job";

            theRoute.setJobClass(GoogleCheckingQuartzJob.class.getName());
            schedulerJobService.save(theRoute);
        return ResponseEntity.status(HttpStatus.OK.value()).body(message);
    }

    @PostMapping("updateJob")
    public ResponseEntity<String> updateJob(@RequestBody RouteJobInfor theRoute){
        String message = "Successfully updated Job";

        theRoute.setJobClass(GoogleCheckingQuartzJob.class.getName());
        schedulerJobService.update(theRoute);
        return ResponseEntity.status(HttpStatus.OK.value()).body(message);
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

    @GetMapping("hello")
    public String hello() {
        return "Hello from Private Driving Service";
    }
}

package org.dontbelate.drivingrouteservice.service;

import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import org.dontbelate.drivingrouteservice.entity.ActionEnum;
import org.dontbelate.drivingrouteservice.entity.DBLAddress;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRouteChangeMessage;
import org.dontbelate.drivingrouteservice.exception.ResourceNotFoundException;
import org.dontbelate.drivingrouteservice.repository.DBLAddressRepository;
import org.dontbelate.drivingrouteservice.repository.DBLDrivingRouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class DrivingRouteService {
    private static final Logger logger = LoggerFactory.getLogger(DrivingRouteService.class);

    @Autowired
    private final StreamBridge streamBridge;
    @Autowired
    private DBLDrivingRouteRepository dblDrivingRouteRepository;
    @Autowired
    private DBLAddressRepository dblAddressRepository;
    public ResponseEntity<DBLDrivingRoute> saveADrivingRoute(DBLDrivingRoute theRoute){
//        try{
            DBLAddress inputStartAddress = theRoute.getStartLocation();

            DBLAddress checkStartAddress = dblAddressRepository.findByStreetIgnoreCaseAndCityIgnoreCaseAndStateIgnoreCaseAndZipCodeIgnoreCase(
                    inputStartAddress.getStreet(), inputStartAddress.getCity(), inputStartAddress.getState(), inputStartAddress.getZipCode()
            );
            DBLAddress inputEndAddress = theRoute.getEndLocation();
            DBLAddress checkEndAddress = dblAddressRepository.findByStreetIgnoreCaseAndCityIgnoreCaseAndStateIgnoreCaseAndZipCodeIgnoreCase(
                    inputEndAddress.getStreet(), inputEndAddress.getCity(), inputEndAddress.getState(), inputEndAddress.getZipCode()
            );

            if(checkStartAddress != null){
                theRoute.setStartLocation(checkStartAddress);
            } else {
                checkStartAddress = dblAddressRepository.save(theRoute.getStartLocation());
                theRoute.setStartLocation(checkStartAddress);
            }

            if(checkEndAddress != null){
                theRoute.setEndLocation(checkEndAddress);
            } else {
                checkEndAddress = dblAddressRepository.save(theRoute.getEndLocation());
                theRoute.setEndLocation(checkEndAddress);
            }

//            if(dblDrivingRouteRepository.existsById(theRoute.getId())){
//                DBLDrivingRouteChangeMessage newMessage = new DBLDrivingRouteChangeMessage(DBLDrivingRoute.class.getTypeName(),
//                        ActionEnum.UPDATED.toString(), theRoute.getId());
//                streamBridge.send("DrivingRouteChangeMessageTopic", MessageBuilder.withPayload(newMessage).build());
//                logger.error("Save Message Sent!");
//            }
            DBLDrivingRoute route= dblDrivingRouteRepository.save(theRoute);

            return ResponseEntity.ok(route);
//        } catch (Exception e){
//            throw  new RuntimeException("Fail to send out message to DrivingRouteChangeMessageTopic");
//
//        }

    }

    public String deletebyRoutebyId(Long id){
        try{
            if(dblDrivingRouteRepository.existsById(id)){

                dblDrivingRouteRepository.deleteById(id);
                DBLDrivingRouteChangeMessage newMessage = new DBLDrivingRouteChangeMessage(DBLDrivingRoute.class.getTypeName(),
                        ActionEnum.DELETED.toString(), id);
                streamBridge.send("DrivingRouteChangeMessageTopic", MessageBuilder.withPayload(newMessage).build());
                return "Item deleted successfully.";
            } else {
                throw new ResourceNotFoundException("DBLDrivingRoute", "id",id );
            }
        }catch (Exception e){
            throw  new RuntimeException("Fail to delete Item or send out message to DrivingRouteChangeMessageTopic");
        }
    }

//    @Observed(name = "drivingroute",
//            contextualName = "getting-drivingroute",
//            lowCardinalityKeyValues = {"drivingrouteType", "drivingrouteType2"})
    public DBLDrivingRoute getRoutebyId(Long id){
        Optional<DBLDrivingRoute> routeOP = dblDrivingRouteRepository.findById(id);
        if (!routeOP.isPresent()) {
            throw new ResourceNotFoundException("DBLDrivingRoute","id",id);
        } else {
            return routeOP.get();
        }
    }


}

package org.dontbelate.drivingrouteservice.service;

import lombok.AllArgsConstructor;
import org.dontbelate.drivingrouteservice.entity.DBLAddress;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;
import org.dontbelate.drivingrouteservice.repository.DBLAddressRepository;
import org.dontbelate.drivingrouteservice.repository.DBLDrivingRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class DrivingRouteService {
    @Autowired
    private DBLDrivingRouteRepository dblDrivingRouteRepository;
    @Autowired
    private DBLAddressRepository dblAddressRepository;
    public DBLDrivingRoute saveADrivingRoute(DBLDrivingRoute theRoute){

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

        return dblDrivingRouteRepository.save(theRoute);
    }


}

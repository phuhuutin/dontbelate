package org.dontbelate.drivingrouteservice.service;

import lombok.AllArgsConstructor;
import org.dontbelate.drivingrouteservice.entity.DBLAddress;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;
import org.dontbelate.drivingrouteservice.repository.DBLDrivingRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class LoadDummyData {
    @Autowired
    private DBLDrivingRouteRepository dBLDrivingRouteRepository;

    public void loadExampleData(){
        DBLAddress ad1 = new DBLAddress();
        ad1.setStreet("4507 S Yakima");
        ad1.setCity("Tacoma");
        ad1.setState("WA");
        ad1.setZipCode("98404");
        DBLAddress ad2 = new DBLAddress();
        ad2.setStreet("1326 E 42nd St");
        ad2.setCity("Tacoma");
        ad2.setState("WA");
        ad2.setZipCode("98404");

        DBLDrivingRoute route1 = new DBLDrivingRoute();
        route1.setRouteName("Working");
        route1.setEndLocation(ad2);
        route1.setStartLocation(ad1);
        route1.setStartTime(LocalTime.of(13,20));
        Set<DayOfWeek> daysOfWeek = new HashSet<>();
        daysOfWeek.add(DayOfWeek.FRIDAY);
        daysOfWeek.add(DayOfWeek.WEDNESDAY);
        route1.setDaysOfWeek(daysOfWeek);

        dBLDrivingRouteRepository.save(route1);
    }

}

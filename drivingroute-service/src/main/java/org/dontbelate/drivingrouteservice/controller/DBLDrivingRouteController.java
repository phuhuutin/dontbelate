package org.dontbelate.drivingrouteservice.controller;

import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;
import org.dontbelate.drivingrouteservice.repository.DBLDrivingRouteRepository;
import org.dontbelate.drivingrouteservice.service.LoadDummyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/routeservice")
public class DBLDrivingRouteController {
    @Autowired
    private DBLDrivingRouteRepository dblDrivingRouteRepository;
    @Autowired
    private LoadDummyData loadDummyData;

    @GetMapping("loaddata")
    public void loadDummyData(){

        loadDummyData.loadExampleData();

    }

    @GetMapping("{id}")
    public String getById(@PathVariable("id") Long routeId){

        return dblDrivingRouteRepository.findById(routeId).get().getDaysOfWeek().toString();

    }

}

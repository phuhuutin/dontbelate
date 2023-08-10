package org.dontbelate.userservice.controller;

import org.dontbelate.userservice.service.LoadDummyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/userservice")
public class DBLUserController {
    @Autowired
    private LoadDummyData loadDummyData;
    @GetMapping("loaddata")
    public void loadDummyData(){

            loadDummyData.loadExampleData();

    }
}

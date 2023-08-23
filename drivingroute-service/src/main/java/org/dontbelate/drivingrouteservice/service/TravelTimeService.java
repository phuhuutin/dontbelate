package org.dontbelate.drivingrouteservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.dontbelate.drivingrouteservice.dto.DBLRouteTimeResponse;
import org.dontbelate.drivingrouteservice.dto.GoogleDistanceMatrixResponse;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;
import org.dontbelate.drivingrouteservice.repository.DBLDrivingRouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Service
public class TravelTimeService {
    private static final Logger logger = LoggerFactory.getLogger(TravelTimeService.class);

    private String myGoogleAppKey;

    private RestTemplate restTemplate;

    private DBLDrivingRouteRepository dblDrivingRouteRepository;
    @Autowired
    public TravelTimeService(@Value("${dontbelate.appKey}") String myGoogleAppKey,
                             RestTemplate restTemplate,DBLDrivingRouteRepository dblDrivingRouteRepository ) {
        this.myGoogleAppKey = myGoogleAppKey;
        this.restTemplate = restTemplate;
        this.dblDrivingRouteRepository = dblDrivingRouteRepository;
    }
    public ResponseEntity<GoogleDistanceMatrixResponse> getRouteTime(Long routeId){
        logger.error("Debug");
        Optional<DBLDrivingRoute> theRoute = dblDrivingRouteRepository.findById(routeId);
        if(theRoute.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try{
                String apiUrl = "https://maps.googleapis.com/maps/api/distancematrix/json" +
                        "?departure_time=now" +
                        "&destinations=" + theRoute.get().getEndLocation().getReadableAddress()+
                        "&origins=" + theRoute.get().getStartLocation().getReadableAddress() +
                        "&key=" + myGoogleAppKey;
                logger.error(apiUrl);
                ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    String jsonResponse = response.getBody();
                    logger.error(jsonResponse);
                    ObjectMapper objectMapper = new ObjectMapper();
                    GoogleDistanceMatrixResponse responseObject = objectMapper.readValue(jsonResponse, GoogleDistanceMatrixResponse.class);
                    logger.error(responseObject.toString());
                    return new ResponseEntity<>(responseObject, HttpStatus.OK);
                } else {
                    logger.error("API call failed with status code: " + response.getStatusCode());
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }


}

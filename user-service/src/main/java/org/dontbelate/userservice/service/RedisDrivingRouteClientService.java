package org.dontbelate.userservice.service;

import org.dontbelate.userservice.dto.DBLDrivingRoute;
import org.dontbelate.userservice.repository.RouteRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RedisDrivingRouteClientService {
    @Autowired
    private DrivingRouteClient drivingRouteClient;
    @Autowired
    private RouteRedisRepository dblDrivingRouteRedisRepository;

    private static final Logger logger = LoggerFactory.getLogger(RedisDrivingRouteClientService.class);

    public DBLDrivingRoute getDBLDrivingRoute(Long routeId){
        DBLDrivingRoute routeFromRedis = checkRedisCache(routeId);
        if (routeFromRedis != null){
            logger.info("I have successfully retrieved an organization {} from the redis cache: {}", routeId, routeFromRedis);
            return routeFromRedis;
        }

        logger.debug("Unable to locate DrivingRoute from the redis cache: {}.", routeId);

        ResponseEntity<DBLDrivingRoute> routeFromClient =  drivingRouteClient.getRoute(routeId);
        if(routeFromClient.getStatusCode() == HttpStatus.OK ){
            logger.info("Getting DrivingRoute from database");
            cacheDBLDrivingRoutetoRedis(routeFromClient.getBody());
            logger.info("caching the DrivingRoute to Redis");
        }

        return routeFromClient.getBody();

    }

    public void cacheInvalidatingById(Long id){
        try{
            if(dblDrivingRouteRedisRepository.existsById(id)){
                dblDrivingRouteRedisRepository.deleteById(id);
                logger.info("Successfully cache-invalidated DrivingRoute Id: {}", id);
            } else {
                logger.info("Nothing need to be done, DrivingRoute Id: {} does not exist in the cache", id);
            }


        } catch (Exception e){
            logger.error("Unable to cache invalidatite {} in Redis. Exception {}", id, e);
        }
    }


    public DBLDrivingRoute checkRedisCache(Long id){
        try {
            return dblDrivingRouteRedisRepository.findById(id).orElse(null);
        }catch (Exception ex){
            logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.  Exception {}", id, ex);
            return null;
        }
    }
    public void cacheDBLDrivingRoutetoRedis(DBLDrivingRoute route){
        try{
            dblDrivingRouteRedisRepository.save(route);
        } catch (Exception e){
            logger.error("Unable to cache organization {} in Redis. Exception {}", route.getId(), e);
        }
    }
}

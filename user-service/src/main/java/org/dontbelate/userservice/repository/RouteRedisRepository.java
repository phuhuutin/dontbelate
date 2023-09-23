package org.dontbelate.userservice.repository;

import org.dontbelate.userservice.dto.DBLDrivingRoute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRedisRepository extends CrudRepository<DBLDrivingRoute,Long> {
}

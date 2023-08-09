package org.dontbelate.drivingrouteservice.repository;

import org.dontbelate.drivingrouteservice.entity.DBLDrivingRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBLDrivingRouteRepository extends JpaRepository<DBLDrivingRoute, Long> {
}

package org.dontbelate.drivingrouteservice.repository;

import org.dontbelate.drivingrouteservice.entity.DBLAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBLAddressRepository extends JpaRepository<DBLAddress, Long> {
}

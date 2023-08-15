package org.dontbelate.drivingrouteservice.repository;

import org.dontbelate.drivingrouteservice.entity.DBLAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DBLAddressRepository extends JpaRepository<DBLAddress, Long> {
    public DBLAddress  findByStreetIgnoreCaseAndCityIgnoreCaseAndStateIgnoreCaseAndZipCodeIgnoreCase(String street, String city, String state, String zipCode);
}

package org.dontbelate.userservice.repository;

import org.dontbelate.userservice.entity.DBLAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBLAddressRepository extends JpaRepository<DBLAddress, Long> {
}

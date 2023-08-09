package org.dontbelate.userservice.repository;

import org.dontbelate.userservice.entity.DBLUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBLUserRepository extends JpaRepository<DBLUser, Long> {
}

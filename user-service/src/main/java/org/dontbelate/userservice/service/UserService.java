package org.dontbelate.userservice.service;

import lombok.AllArgsConstructor;
import org.dontbelate.userservice.dto.DBLDrivingRoute;
import org.dontbelate.userservice.dto.DBLUserDTO;
import org.dontbelate.userservice.entity.DBLAddress;
import org.dontbelate.userservice.entity.DBLUser;
import org.dontbelate.userservice.exception.ResourceNotFoundException;
import org.dontbelate.userservice.repository.DBLUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private DBLUserRepository dblUserRepository;
    @Autowired
    private DrivingRouteClient drivingRouteClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public DBLUser saveUser(DBLUserDTO dblUserDTO){
        DBLAddress theAddress = new DBLAddress(dblUserDTO.getStreet(), dblUserDTO.getCity(), dblUserDTO.getState(), dblUserDTO.getZipCode());
        DBLUser theUser = new DBLUser();
        theUser.setAddress(theAddress);
        theUser.setUsername(dblUserDTO.getUsername());
        theUser.setEmail(dblUserDTO.getEmail());

        return dblUserRepository.save(theUser);

    }

    public ResponseEntity<DBLUser> getUserById(Long userId) {
        Optional<DBLUser> optionalUser = dblUserRepository.findById(userId);

        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            throw new ResourceNotFoundException("User", "userId", userId);
        }
    }


}

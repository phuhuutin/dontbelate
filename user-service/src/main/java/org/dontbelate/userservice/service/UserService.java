package org.dontbelate.userservice.service;

import lombok.AllArgsConstructor;
import org.dontbelate.userservice.dto.DBLUserDTO;
import org.dontbelate.userservice.entity.DBLAddress;
import org.dontbelate.userservice.entity.DBLUser;
import org.dontbelate.userservice.repository.DBLUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

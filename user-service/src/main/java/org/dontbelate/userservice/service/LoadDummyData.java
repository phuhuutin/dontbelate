package org.dontbelate.userservice.service;
import lombok.AllArgsConstructor;
import org.dontbelate.userservice.entity.DBLAddress;
import org.dontbelate.userservice.entity.DBLUser;
import org.dontbelate.userservice.repository.DBLUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class LoadDummyData {
    @Autowired
    private DBLUserRepository dBLUserRepository;

    public void loadExampleData() {
        DBLAddress address = new DBLAddress();
        address.setStreet("4507 S Yakima");
        address.setCity("Tacoma");
        address.setState("WA");
        DBLUser user = new DBLUser();
        user.setUsername("phuhuutin");
        user.setEmail("phuhuutin@gmail.com");
        user.setAddress(address);
        dBLUserRepository.save(user);

    }


}

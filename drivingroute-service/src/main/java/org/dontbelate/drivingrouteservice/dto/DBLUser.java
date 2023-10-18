package org.dontbelate.drivingrouteservice.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dontbelate.drivingrouteservice.entity.DBLAddress;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBLUser {

    private Long id;

    private String username;
    private String email;

    private DBLAddress address;
    @Override
    public String toString(){
        return "DBLUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address.toString() +
                '}';
    }

}
package org.dontbelate.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="addresses")
public class DBLAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String street;

    private String city;

    private String state;

    private String zipCode;

    private double latitude;
    private double longitude;

    public DBLAddress(String theStreet, String theCity, String theState, String theZipCode){
        this.street = theStreet;
        this.city = theCity;
        this.state = theState;
        this.zipCode = theZipCode;
        this.latitude  = 0;
        this.longitude = 0;
    }
    @Override
    public String toString(){
        return street + ", " + city + ", " + state + " " + zipCode;
    }
}

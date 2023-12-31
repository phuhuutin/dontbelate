package org.dontbelate.drivingrouteservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public String getReadableAddress() {
        return street + ", " + city + ", " + state + " " + zipCode;
    }
}

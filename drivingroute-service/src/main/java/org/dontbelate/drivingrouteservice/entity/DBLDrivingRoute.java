package org.dontbelate.drivingrouteservice.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drivingroute")
public class DBLDrivingRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String routeName;
    private String startLocation;
    private String endLocation;
    private String expectedDuration;

    private Long userID;
}

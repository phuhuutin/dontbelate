package org.dontbelate.drivingrouteservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GoogleDistanceMatrixResponse {
    @JsonProperty("destination_addresses")
    private List<String> destinationAddresses;

    @JsonProperty("origin_addresses")
    private List<String> originAddresses;
    @JsonProperty("rows")
    private List<Row> rows;
    @JsonProperty("status")
    private String status;

    // Constructors, getters, setters, etc.

    @Override
    public String toString() {
        return "DistanceMatrixResponse{" +
                "destinationAddresses=" + destinationAddresses +
                ", originAddresses=" + originAddresses +
                ", rows=" + rows +
                ", status='" + status + '\'' +
                '}';
    }
}

class Row {
    @JsonProperty("elements")
    private List<Element> elements;

    // Constructors, getters, setters, etc.

    @Override
    public String toString() {
        return "Row{" +
                "elements=" + elements +
                '}';
    }
}

class Element {
    @JsonProperty("distance")
    private Distance distance;
    @JsonProperty("duration")
    private Duration duration;
    @JsonProperty("duration_in_traffic")
    private Duration durationInTraffic;
    @JsonProperty("status")
    private String status;

    // Constructors, getters, setters, etc.

    @Override
    public String toString() {
        return "Element{" +
                "distance=" + distance +
                ", duration=" + duration +
                ", status='" + status + '\'' +
                '}';
    }
}

class Distance {
    @JsonProperty("text")
    private String text;
    @JsonProperty("value")
    private int value;

    // Constructors, getters, setters, etc.

    @Override
    public String toString() {
        return "Distance{" +
                "text='" + text + '\'' +
                ", value=" + value +
                '}';
    }
}

class Duration {
    @JsonProperty("text")
    private String text;
    @JsonProperty("value")
    private int value;

    // Constructors, getters, setters, etc.

    @Override
    public String toString() {
        return "Duration{" +
                "text='" + text + '\'' +
                ", value=" + value +
                '}';
    }
}
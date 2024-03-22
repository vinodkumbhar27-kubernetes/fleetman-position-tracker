package com.virtualpairprogrammers.tracker.domain;

import java.math.BigDecimal;
import java.util.Date;

public class VehicleBuilder {
    private String name;
    private BigDecimal lat;
    private BigDecimal lng;
    private Date timestamp;
    private double speed; // Change type to double

    public VehicleBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public VehicleBuilder withLat(BigDecimal lat) {
        this.lat = lat;
        return this;
    }

    public VehicleBuilder withLng(BigDecimal lng) {
        this.lng = lng;
        return this;
    }

    public VehicleBuilder withTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    // Change the parameter type to double
    public VehicleBuilder withSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    public VehiclePosition build() {
        return new VehiclePosition(name, lat, lng, timestamp, speed);
    }
}

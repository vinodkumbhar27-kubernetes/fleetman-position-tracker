package com.virtualpairprogrammers.tracker.messaging;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.virtualpairprogrammers.tracker.data.Data;
import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

@Component
public class MessageProcessor {
    
    @Autowired
    private Data data;
    
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @JmsListener(destination="${fleetman.position.queue}")
    public void processPositionMessageFromQueue(Map<String, String> incomingMessage ) throws ParseException 
    {
        String positionDatestamp = incomingMessage.get("time");
        Date convertedDatestamp = format.parse(positionDatestamp);
        
        // Hardcoded speed value (e.g., 50 km/h)
        double speed = 50.0;
        
        VehiclePosition newReport = new VehiclePosition(
                incomingMessage.get("vehicle"),
                new BigDecimal(incomingMessage.get("lat")),
                new BigDecimal(incomingMessage.get("long")),
                convertedDatestamp,
                speed);
                                      
        data.updatePosition(newReport);
    }
}

package com.virtualpairprogrammers.tracker.domain;

import java.math.BigDecimal;
import java.util.Date;

public class VehiclePosition {
    private String name;
    private BigDecimal lat;
    private BigDecimal lng;
    private Date timestamp;
    private double speed;

    public VehiclePosition(String name, BigDecimal lat, BigDecimal lng, Date timestamp, double speed) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
        this.speed = speed;
    }

    // Getters and setters
}

package com.virtualpairprogrammers.tracker.domain;

import java.math.BigDecimal;
import java.util.Date;

public class VehicleBuilder {
    private String name;
    private BigDecimal lat;
    private BigDecimal lng;
    private Date timestamp;
    private double speed;

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

    public VehicleBuilder withSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    public VehiclePosition build() {
        return new VehiclePosition(name, lat, lng, timestamp, speed);
    }
}

package com.virtualpairprogrammers.tracker.data;

import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

public class Data {
    public void updatePosition(VehiclePosition position) {
        // Implementation to update position data
    }
}

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
        
        // Hardcoded speed value (e.g., 63.5 mph)
        BigDecimal speed = new BigDecimal("63.5");
        
        VehiclePosition newReport = new VehiclePosition(
                incomingMessage.get("vehicle"),
                new BigDecimal(incomingMessage.get("lat")),
                new BigDecimal(incomingMessage.get("long")),
                convertedDatestamp,
                speed.doubleValue());
                                      
        data.updatePosition(newReport);
    }
}

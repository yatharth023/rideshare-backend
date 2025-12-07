package org.example.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rides")
public class Ride {

    @Id
    private String id;

    private String userId;        // passenger
    private String driverId;      // driver

    private String pickupLocation;
    private String dropLocation;

    // REQUESTED / ACCEPTED / COMPLETED
    private String status;

    private Date createdAt;
}

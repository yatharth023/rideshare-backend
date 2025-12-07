package org.example.rideshare.controller;

import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverRideController {

    @Autowired
    private RideService rideService;

    // DRIVER: view all pending ride requests
    @GetMapping("/rides/requests")
    public ResponseEntity<List<RideResponse>> getPendingRides(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(rideService.getPendingRides(username));
    }

    // DRIVER: accept ride
    @PostMapping("/rides/{rideId}/accept")
    public ResponseEntity<RideResponse> acceptRide(
            @PathVariable String rideId,
            Authentication authentication
    ) {
        String username = authentication.getName();
        return ResponseEntity.ok(rideService.acceptRide(rideId, username));
    }
}

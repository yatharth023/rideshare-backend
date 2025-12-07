package org.example.rideshare.controller;

import jakarta.validation.Valid;
import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    @Autowired
    private RideService rideService;

    // USER: create ride
    @PostMapping("/rides")
    public ResponseEntity<RideResponse> createRide(
            @Valid @RequestBody CreateRideRequest request,
            Authentication authentication
    ) {
        String username = authentication.getName();
        return ResponseEntity.ok(rideService.createRide(request, username));
    }

    // USER: get own rides
    @GetMapping("/user/rides")
    public ResponseEntity<List<RideResponse>> getUserRides(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(rideService.getUserRides(username));
    }

    // USER or DRIVER: complete ride
    @PostMapping("/rides/{rideId}/complete")
    public ResponseEntity<RideResponse> completeRide(
            @PathVariable String rideId,
            Authentication authentication
    ) {
        String username = authentication.getName();
        return ResponseEntity.ok(rideService.completeRide(rideId, username));
    }
}

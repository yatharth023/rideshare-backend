package org.example.rideshare.service;

import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.exception.BadRequestException;
import org.example.rideshare.exception.NotFoundException;
import org.example.rideshare.model.Ride;
import org.example.rideshare.model.User;
import org.example.rideshare.repository.RideRepository;
import org.example.rideshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    public RideResponse createRide(CreateRideRequest request, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (!"ROLE_USER".equals(user.getRole())) {
            throw new BadRequestException("Only USER can request rides");
        }

        Ride ride = new Ride();
        ride.setUserId(user.getId());
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setStatus("REQUESTED");
        ride.setCreatedAt(new Date());

        Ride saved = rideRepository.save(ride);

        return toResponse(saved);
    }

    public List<RideResponse> getUserRides(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));

        return rideRepository.findByUserId(user.getId())
                .stream().map(this::toResponse).toList();
    }

    public List<RideResponse> getPendingRides(String username) {
        User driver = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (!"ROLE_DRIVER".equals(driver.getRole())) {
            throw new BadRequestException("Only DRIVER can view pending rides");
        }

        return rideRepository.findByStatus("REQUESTED")
                .stream().map(this::toResponse).toList();
    }

    public RideResponse acceptRide(String rideId, String username) {
        User driver = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (!"ROLE_DRIVER".equals(driver.getRole())) {
            throw new BadRequestException("Only DRIVER can accept rides");
        }

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!"REQUESTED".equals(ride.getStatus())) {
            throw new BadRequestException("Ride is not in REQUESTED status");
        }

        ride.setDriverId(driver.getId());
        ride.setStatus("ACCEPTED");

        Ride saved = rideRepository.save(ride);
        return toResponse(saved);
    }

    public RideResponse completeRide(String rideId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!"ACCEPTED".equals(ride.getStatus())) {
            throw new BadRequestException("Ride is not in ACCEPTED status");
        }

        // Either passenger or driver can complete
        if (!user.getId().equals(ride.getUserId())
                && (ride.getDriverId() == null || !user.getId().equals(ride.getDriverId()))) {
            throw new BadRequestException("You are not allowed to complete this ride");
        }

        ride.setStatus("COMPLETED");

        Ride saved = rideRepository.save(ride);
        return toResponse(saved);
    }

    private RideResponse toResponse(Ride ride) {
        return new RideResponse(
                ride.getId(),
                ride.getUserId(),
                ride.getDriverId(),
                ride.getPickupLocation(),
                ride.getDropLocation(),
                ride.getStatus(),
                ride.getCreatedAt()
        );
    }
}

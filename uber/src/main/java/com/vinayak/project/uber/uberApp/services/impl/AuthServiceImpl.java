package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.dto.DriverDto;
import com.vinayak.project.uber.uberApp.dto.SignupDto;
import com.vinayak.project.uber.uberApp.dto.UserDto;
import com.vinayak.project.uber.uberApp.entities.Driver;
import com.vinayak.project.uber.uberApp.entities.Rider;
import com.vinayak.project.uber.uberApp.entities.User;
import com.vinayak.project.uber.uberApp.entities.enums.Role;
import com.vinayak.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.vinayak.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.vinayak.project.uber.uberApp.repositories.UserRepo;
import com.vinayak.project.uber.uberApp.services.AuthService;
import com.vinayak.project.uber.uberApp.services.DriverService;
import com.vinayak.project.uber.uberApp.services.RiderService;
import com.vinayak.project.uber.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.vinayak.project.uber.uberApp.entities.enums.Role.DRIVER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    @Transactional // To avoid Data Inconsistency, like after saving a user there may be a issue in creating a Rider , so the whole process should be rolled back
    public UserDto signup(SignupDto signupDto) {
        User user= userRepo.findByEmail(signupDto.getEmail()).orElse(null);
        if(user !=null){
            throw new RuntimeException("Cannot signup, user already exists with this Email"+signupDto.getEmail());
        }

        User mappedUser= modelMapper.map(signupDto,User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser= userRepo.save(mappedUser);

        //Create user related entities
       riderService.createNewRider(savedUser);

       //Add wallet related services--------
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId, String vehicleId ) {
       User user= userRepo.findById(userId)
               .orElseThrow(()-> new ResourceNotFoundException("user not found with id "+ userId));

       if(user.getRoles().contains(DRIVER))
           throw new RuntimeConflictException("User with userid "+userId+ "is already a driver");

       Driver createDriver= Driver.builder()
               .user(user)
               .rating(0.0)
               .vehicleId(vehicleId)
               .available(true)
               .build();

       user.getRoles().add(DRIVER);
       userRepo.save(user);

       Driver savedDriver=driverService.createNewDriver(createDriver);

        return modelMapper.map(savedDriver, DriverDto.class);
    }
}

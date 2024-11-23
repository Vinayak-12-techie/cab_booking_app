package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.dto.DriverDto;
import com.vinayak.project.uber.uberApp.dto.SignupDto;
import com.vinayak.project.uber.uberApp.dto.UserDto;
import com.vinayak.project.uber.uberApp.entities.Driver;
import com.vinayak.project.uber.uberApp.entities.User;
import com.vinayak.project.uber.uberApp.entities.enums.Role;
import com.vinayak.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.vinayak.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.vinayak.project.uber.uberApp.repositories.UserRepo;
import com.vinayak.project.uber.uberApp.security.JWTService;
import com.vinayak.project.uber.uberApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;

    @Override
    public String[] login(String email, String password) {

        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        User user= (User) authentication.getPrincipal();
        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);

        return new String[]{accessToken,refreshToken};
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
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
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

    @Override
    public String refreshToken(String refreshToken) {
        Long userId= jwtService.getUserIdFromToken(refreshToken);
        User user=userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id : "+userId));

        return jwtService.generateAccessToken(user);
    }
}

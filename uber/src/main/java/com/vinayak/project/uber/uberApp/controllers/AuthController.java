package com.vinayak.project.uber.uberApp.controllers;


import com.vinayak.project.uber.uberApp.dto.DriverDto;
import com.vinayak.project.uber.uberApp.dto.OnBoardDriverDto;
import com.vinayak.project.uber.uberApp.dto.SignupDto;
import com.vinayak.project.uber.uberApp.dto.UserDto;
import com.vinayak.project.uber.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    ResponseEntity<UserDto> signUp(@RequestBody SignupDto signupDto){
        return new ResponseEntity<>(authService.signup(signupDto), HttpStatus.CREATED);
    }

    @PostMapping("/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId, @RequestBody OnBoardDriverDto onBoardDriverDto){
        return new ResponseEntity<>(authService.onboardNewDriver(userId,onBoardDriverDto.getVehicleId()),HttpStatus.CREATED);
    }
}

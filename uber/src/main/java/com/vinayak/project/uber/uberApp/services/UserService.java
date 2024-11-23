package com.vinayak.project.uber.uberApp.services;

import com.vinayak.project.uber.uberApp.entities.User;
import com.vinayak.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.vinayak.project.uber.uberApp.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElse(null);
    }

    public User getUserById(Long id){
        return userRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No user found with the given ID"));
    }
}

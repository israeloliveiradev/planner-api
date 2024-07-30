package com.israeloliveiradev.planner.services;

import com.israeloliveiradev.planner.entities.Username;
import com.israeloliveiradev.planner.entities.UsernameLogin;
import com.israeloliveiradev.planner.repositories.UsernameRepository;
import com.israeloliveiradev.planner.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UsernameService {

    @Autowired
    private UsernameRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<Username> userNameSignUp(Username username) {

        if (userRepository.findByUsername(username.getUsername()).isPresent())
            return Optional.empty();

        username.setPassword(encryptPassword((username.getPassword())));

        return Optional.of(userRepository.save(username));

    }

    public Optional<Username> PutUsername(Username username) {

        if(userRepository.findById(username.getId()).isPresent()) {

            Optional<Username> searchUsername = userRepository.findByUsername(username.getUsername());

            if ( (searchUsername.isPresent()) && ( searchUsername.get().getId() != username.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists!", null);

            username.setPassword(encryptPassword(username.getPassword()));

            return Optional.ofNullable(userRepository.save(username));

        }

        return Optional.empty();

    }

    public Optional<UsernameLogin> AuthenticateUsers(Optional<UsernameLogin> usernameLogin) {


        var credentials = new UsernamePasswordAuthenticationToken(usernameLogin.get().getUsername(), usernameLogin.get().getPassword());

        Authentication authentication = authenticationManager.authenticate(credentials);



        if (authentication.isAuthenticated()) {

            Optional<Username> username = userRepository.findByUsername(usernameLogin.get().getUsername());

            if (username.isPresent()) {


                usernameLogin.get().setId(username.get().getId());
                usernameLogin.get().setUsername(username.get().getUsername());
                usernameLogin.get().setPassword(username.get().getPassword());
                usernameLogin.get().setToken(createToken(usernameLogin.get().getUsername()));
                usernameLogin.get().setPassword(" ");



                return usernameLogin;

            }

        }

        return Optional.empty();

    }

    private String encryptPassword(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);

    }

    private String createToken(String username) {
        return "Bearer " + jwtService.generateToken(username);
    }

}

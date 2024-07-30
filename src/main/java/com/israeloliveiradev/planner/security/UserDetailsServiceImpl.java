package com.israeloliveiradev.planner.security;

import com.israeloliveiradev.planner.entities.Username;
import com.israeloliveiradev.planner.repositories.UsernameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsernameRepository usernameRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Username> username = usernameRepository.findByUsername(userName);
        if (username.isPresent())
            return new UserDetailsImpl(username.get());
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
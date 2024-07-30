package com.israeloliveiradev.planner.repositories;

import com.israeloliveiradev.planner.entities.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsernameRepository extends JpaRepository<Username, UUID> {

    public Optional<Username> findByUsername(String username);
}

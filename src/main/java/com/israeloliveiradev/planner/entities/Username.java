package com.israeloliveiradev.planner.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usernames")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Username {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Email
    @Column(name = "username", nullable = false)
    private String username;

    @Size(min = 6)
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "username", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("username")
    private List<Trip> tripList;

    public Username(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;

    }
}

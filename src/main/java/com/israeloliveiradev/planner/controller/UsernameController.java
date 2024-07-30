package com.israeloliveiradev.planner.controller;

import com.israeloliveiradev.planner.entities.Username;
import com.israeloliveiradev.planner.entities.UsernameLogin;
import com.israeloliveiradev.planner.repositories.UsernameRepository;
import com.israeloliveiradev.planner.services.UsernameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/username")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsernameController {

    @Autowired
    private UsernameService usernameService;


    @Autowired
    private UsernameRepository usernameRepository;

//    @GetMapping("/all")
//    public ResponseEntity<List<Usuario>> getAll() {
//        return ResponseEntity.ok(usuariorepository.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
//        return usuariorepository.findById(id)
//                .map(resposta -> ResponseEntity.ok(resposta))
//                .orElse(ResponseEntity.notFound().build());
//    }

    @PostMapping("/signing")
    public ResponseEntity<UsernameLogin> AuthenticateUsers(@RequestBody Optional<UsernameLogin> usernameLogin) {
        return usernameService.AuthenticateUsers(usernameLogin)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<Username> SignupUsers(@RequestBody @Valid Username username) {
        return usernameService.userNameSignUp(username)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

//    @PutMapping("/atualizar")
//    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) {
//        return usuarioService.atualizarUsuario(usuario)
//                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
//                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
//    }
}

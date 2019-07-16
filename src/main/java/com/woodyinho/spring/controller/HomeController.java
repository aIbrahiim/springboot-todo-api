package com.woodyinho.spring.controller;

import com.woodyinho.spring.model.AppUser;
import com.woodyinho.spring.model.Todo;
import com.woodyinho.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")

public class HomeController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@Valid @RequestBody AppUser appUser){

        userService.saveUser(appUser);
        String result = "User created";
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}

package com.project.tejas.userdetails.controller;

import com.project.tejas.userdetails.dto.UserDetailsDTO;
import com.project.tejas.userdetails.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userinfo")
public class UserDetailsController {
    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/fetchUser/{id}")
    public ResponseEntity<UserDetailsDTO> fetchUserById(@PathVariable Integer id){
        return userDetailsService.getUser(id);
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserDetailsDTO> addNewUser(@RequestBody UserDetailsDTO userDetailsDTO){
        UserDetailsDTO newUser = userDetailsService.addUserToDb(userDetailsDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}

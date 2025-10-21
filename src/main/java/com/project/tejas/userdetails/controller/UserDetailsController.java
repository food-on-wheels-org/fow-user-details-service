package com.project.tejas.userdetails.controller;

import com.project.tejas.userdetails.dto.UserDetailsDTO;
import com.project.tejas.userdetails.service.UserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userinfo")
@CrossOrigin
@Tag(name = "User Details Controller", description = "Endpoints for managing User profile details")
public class UserDetailsController {
    @Autowired
    UserDetailsService userDetailsService;

    @Operation(summary = "Fetch User by ID", description = "Retrieves user details based on the provided user ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User details fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/fetchUser/{id}")
    public ResponseEntity<UserDetailsDTO> fetchUserById(@PathVariable Integer id){
        return userDetailsService.getUser(id);
    }

    @Operation(summary = "Add a new User", description = "Creates a new user record in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/addUser")
    public ResponseEntity<UserDetailsDTO> addNewUser(@RequestBody UserDetailsDTO userDetailsDTO){
        UserDetailsDTO newUser = userDetailsService.addUserToDb(userDetailsDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}

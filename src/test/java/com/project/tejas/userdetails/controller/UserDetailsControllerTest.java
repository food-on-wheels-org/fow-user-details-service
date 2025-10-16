package com.project.tejas.userdetails.controller;

import com.project.tejas.userdetails.dto.UserDetailsDTO;
import com.project.tejas.userdetails.service.UserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserDetailsControllerTest {

    @Mock
    UserDetailsService userDetailsService;

    @InjectMocks
    UserDetailsController userDetailsController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchUserById(){
        Integer mockId = 1;
        UserDetailsDTO mockUser = new UserDetailsDTO();
        when(userDetailsService.getUser(mockId)).thenReturn(new ResponseEntity<>(mockUser, HttpStatus.OK));

        ResponseEntity<UserDetailsDTO> response = userDetailsController.fetchUserById(mockId);

        assert HttpStatus.OK == response.getStatusCode();
        assert mockUser == response.getBody();
        verify(userDetailsService, times(1)).getUser(mockId);
    }

    @Test
    public void testAddNewUser(){
        UserDetailsDTO mockUserDTO = new UserDetailsDTO(1, "Username 1", "password1", "Address 1", "city 1");
        when(userDetailsService.addUserToDb(mockUserDTO)).thenReturn(mockUserDTO);

        ResponseEntity<UserDetailsDTO> response = userDetailsController.addNewUser(mockUserDTO);

        assertEquals(mockUserDTO, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userDetailsService, times(1)).addUserToDb(mockUserDTO);
    }
}

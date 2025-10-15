package com.project.tejas.userdetails.service;

import com.project.tejas.userdetails.dto.UserDetailsDTO;
import com.project.tejas.userdetails.entity.UserDetails;
import com.project.tejas.userdetails.mapper.UserDetailsMapper;
import com.project.tejas.userdetails.repo.UserDetailsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserDetailsServiceTest {

    @Mock
    UserDetailsRepo userDetailsRepo;

    @InjectMocks
    UserDetailsService userDetailsService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUser_user_isPresent(){
        Integer mockId = 1;
        UserDetails mockUser = new UserDetails(1, "Username1", "password1", "Address 1", "city 1");
        when(userDetailsRepo.findById(mockId)).thenReturn(Optional.of(mockUser));

        ResponseEntity<UserDetailsDTO> response = userDetailsService.getUser(mockId);

        assertEquals(UserDetailsMapper.INSTANCE.mapUserToUserDetailsDTO(mockUser), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userDetailsRepo, times(1)).findById(mockId);
    }

    @Test
    public void testGetUser_user_notPresent(){
        Integer mockId = 1;
        UserDetails mockUser = new UserDetails(1, "Username1", "password1", "Address 1", "city 1");
        when(userDetailsRepo.findById(mockId)).thenReturn(Optional.empty());

        ResponseEntity<UserDetailsDTO> response = userDetailsService.getUser(mockId);

        assertEquals(null, response.getBody());
        assert HttpStatus.NOT_FOUND == response.getStatusCode();
        verify(userDetailsRepo, times(1)).findById(mockId);
    }

    @Test
    public void testAddUserToDb(){
        UserDetails mockUser = new UserDetails(1, "username1", "password1", "address1", "city1");
        UserDetailsDTO mockUserDTO = UserDetailsMapper.INSTANCE.mapUserToUserDetailsDTO(mockUser);
        when(userDetailsRepo.save(mockUser)).thenReturn(mockUser);

        UserDetailsDTO resultDTO = userDetailsService.addUserToDb(mockUserDTO);

        assertEquals(mockUserDTO, resultDTO);
        verify(userDetailsRepo, times(1)).save(mockUser);
    }

}

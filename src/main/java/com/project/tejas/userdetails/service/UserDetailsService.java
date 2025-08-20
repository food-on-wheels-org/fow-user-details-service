package com.project.tejas.userdetails.service;

import com.project.tejas.userdetails.dto.UserDetailsDTO;
import com.project.tejas.userdetails.entity.UserDetails;
import com.project.tejas.userdetails.mapper.UserDetailsMapper;
import com.project.tejas.userdetails.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService {
    @Autowired
    UserDetailsRepo userDetailsRepo;

    public ResponseEntity<UserDetailsDTO> getUser(Integer id){
        Optional<UserDetails> fetchedUser = userDetailsRepo.findById(id);
        if(fetchedUser.isPresent()){
            return new ResponseEntity<>(UserDetailsMapper.INSTANCE.mapUserToUserDetailsDTO(fetchedUser.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public UserDetailsDTO addUserToDb(UserDetailsDTO userDetailsDTO){
        UserDetails addedUser = userDetailsRepo.save(UserDetailsMapper.INSTANCE.mapUserDTOToUser(userDetailsDTO));
        return UserDetailsMapper.INSTANCE.mapUserToUserDetailsDTO(addedUser);
    }
}

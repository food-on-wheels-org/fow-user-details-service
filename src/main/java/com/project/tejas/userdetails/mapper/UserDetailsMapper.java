package com.project.tejas.userdetails.mapper;

import com.project.tejas.userdetails.dto.UserDetailsDTO;
import com.project.tejas.userdetails.entity.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDetailsMapper {
    UserDetailsMapper INSTANCE = Mappers.getMapper(UserDetailsMapper.class);
    UserDetails mapUserDTOToUser(UserDetailsDTO userDetailsDTO);
    UserDetailsDTO mapUserToUserDetailsDTO(UserDetails userDetails);
}

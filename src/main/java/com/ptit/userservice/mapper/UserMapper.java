package com.ptit.userservice.mapper;


import com.ptit.userservice.dto.UserDTO;
import com.ptit.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
    private ModelMapper modelMapper;
    public User convertToEntity(UserDTO userDTO){
        User user=modelMapper.map(userDTO,User.class);
        return user;
    }
    public UserDTO convertToDto(User user){
        UserDTO userDTO=modelMapper.map(user,UserDTO.class);
        return userDTO;
    }
}

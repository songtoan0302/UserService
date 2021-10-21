package com.ptit.userservice.service;

import com.ptit.userservice.dto.UserDTO;
import com.ptit.userservice.model.User;

import java.util.List;

public interface UserService {
    UserDTO getUser(int id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO, int id);

    void deleteUser(int id);

    List<UserDTO> getAll();
}

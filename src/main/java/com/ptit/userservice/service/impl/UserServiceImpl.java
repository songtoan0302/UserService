package com.ptit.userservice.service.impl;

import com.ptit.userservice.dto.UserDTO;
import com.ptit.userservice.exception.UserNotFoundException;
import com.ptit.userservice.mapper.UserMapper;
import com.ptit.userservice.model.User;
import com.ptit.userservice.repository.RedisCacheRepository;
import com.ptit.userservice.repository.UserRepository;
import com.ptit.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RedisCacheRepository redisCacheRepository;
    @Autowired
    private  UserMapper userMapper;


    @Override
    public UserDTO getUser(int id) {
        User userCache = redisCacheRepository.findById(id);
        if (!Objects.nonNull(userCache)) {
            User userRepo = userRepository.getById(id);
            if (!Objects.nonNull(userRepo)) throw new UserNotFoundException();
            redisCacheRepository.save(userRepo);
            UserDTO userDTO = userMapper.convertToDto(userRepo);
            return userDTO;
        } else {
            UserDTO userDTO = userMapper.convertToDto(userCache);
            return userDTO;
        }
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User userCreate = userMapper.convertToEntity(userDTO);
        userRepository.save(userCreate);
        UserDTO userDTOCreated = userMapper.convertToDto(userCreate);
        return userDTOCreated;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, int id) {
        User user = userMapper.convertToEntity(userDTO);
        User userUpdate = userRepository.getById(id);
        if (!Objects.nonNull(userUpdate) || !Objects.nonNull(user)) {
            throw new UserNotFoundException();
        }
        userUpdate.setName(user.getName());
        userUpdate.setAddress(user.getAddress());
        UserDTO userDTOUpdated = userMapper.convertToDto(userUpdate);
        return userDTOUpdated;
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.getById(id);
        if (Objects.nonNull(user)) throw new UserNotFoundException();
        userRepository.deleteById(id);
        redisCacheRepository.deleteUser(id);


    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = userMapper.convertToDto(user);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

}

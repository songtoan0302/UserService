package com.ptit.userservice.controller;

import com.ptit.userservice.dto.UserDTO;
import com.ptit.userservice.mapper.UserMapper;
import com.ptit.userservice.model.User;
import com.ptit.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/users")
public class UserController {
    private final UserService userService;
    private UserMapper userMapper;

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        UserDTO userDTO = userService.getUser(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO userDTOCreated = userService.createUser(userDTO);
        return new ResponseEntity<>(userDTOCreated, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("id") int id) {
        UserDTO userDTOUpdated = userService.updateUser(userDTO, id);
        return new ResponseEntity<>(userDTOUpdated, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);
        return new ResponseEntity<>("Deleted User Successfully",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> userDTOS = userService.getAll();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }
}

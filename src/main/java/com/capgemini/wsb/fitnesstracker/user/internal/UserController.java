package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    private final UserSimpleMapper userSimpleMapper;

    private final UserEmailInfoMapper userEmailInfoMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userMapper.toEntity(userDto)), CREATED);
    }

    @GetMapping("/simple")
    public List<UserSimpleView> getAllUserSimpleInformation(){
        return userService.findAllUsers().stream().map(userSimpleMapper::toDto).toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable @RequestParam long id){
        return userService.deleteUser(id).map(userMapper::toDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/emailInfo")
    public List<UserEmailInfoView> getAllUsersByEmailContaining(@RequestParam String query){
        return userService.findByEmailContaining(query).stream().map(userEmailInfoMapper::toDto).toList();
    }

    @GetMapping("/maxAge")
    public List<UserDto> getAllUsersWithAgeGreaterThan(@RequestParam long maxAge){
        return userService.findByAgeGreaterThan(maxAge).stream().map(userMapper::toDto).toList();
    }

    @PutMapping("/userUpdateInfo")
    public ResponseEntity<User> updateUserEmail(@RequestBody UserUpdateInfo userUpdateInfo){
        return userService.updateUserEmail(userUpdateInfo.id(), userUpdateInfo.email()).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
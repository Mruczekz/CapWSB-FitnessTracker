package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long id){
        return userService.getUser(id).map(userMapper::toDto).get();
    }

    @GetMapping("/simple")
    public List<UserSimpleView> getAllUserSimpleInformation(){
        return userService.findAllUsers().stream().map(userSimpleMapper::toDto).toList();
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Long> deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(userId, NO_CONTENT);
    }

    @GetMapping("/email")
    public List<UserEmailInfoView> getAllUsersByEmailContaining(@RequestParam("email") String email){
        return userService.findByEmailContaining(email).stream().map(userEmailInfoMapper::toDto).toList();
    }

    @GetMapping("/maxAge")
    public List<UserDto> getAllUsersWithAgeGreaterThan(@RequestParam long maxAge){
        return userService.findByAgeGreaterThan(maxAge).stream().map(userMapper::toDto).toList();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDto changedUser) {
        return new ResponseEntity<>(userService.updateUser(userId, userMapper.toEntitySave(changedUser)), ACCEPTED);
    }
}
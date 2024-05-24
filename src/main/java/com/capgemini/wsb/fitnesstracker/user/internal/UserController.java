package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailInfoView;
import com.capgemini.wsb.fitnesstracker.user.api.UserSimpleView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

        // TODO: saveUser with Service and return User
        return null;
    }

    @GetMapping("/simple")
    public List<UserSimpleView> getAllUserSimpleInformation(){
        return userService.findAllUsers().stream().map(userSimpleMapper::toDto).toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@RequestParam long id){
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
}
package dev.tavin.go_task.controller;

import dev.tavin.go_task.infra.dto.user.UserRequestDto;
import dev.tavin.go_task.infra.dto.user.UserResponseDto;
import dev.tavin.go_task.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
public class UserController {

    private final UserService  userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
   }

   @DeleteMapping()
    public ResponseEntity<Void> deleteCurrentUser() {
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @PutMapping()
    public ResponseEntity<UserResponseDto> updateCurrentUser(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userService.updateUser(userRequestDto), HttpStatus.OK);
   }
}

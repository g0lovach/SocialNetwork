package com.example.exampleauth.controllers;

import com.example.exampleauth.config.exceptions.ErrorResponse;
import com.example.exampleauth.config.exceptions.FriendshipNotCreated;
import com.example.exampleauth.config.exceptions.UserNotExist;
import com.example.exampleauth.dtos.UserDto;
import com.example.exampleauth.enities.User;
import com.example.exampleauth.services.FriendshipService;
import com.example.exampleauth.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/")
public class UserController {
    private final UserService userService;
    private final FriendshipService friendshipService;



    @GetMapping("user/{id}/friends")
    ResponseEntity<Map<String, Long>> getFriends(@PathVariable("id") Long id){
        return new ResponseEntity<>(friendshipService.getUserFriends(id), HttpStatus.OK);
    }

    @DeleteMapping("user/{id}/friends/{exFriendId}")
    ResponseEntity<HttpStatus> deleteFriend(@PathVariable("id") Long id, @PathVariable("exFriendId") Long exFriendId ){
        friendshipService.deleteFriend(id, exFriendId);
        return ResponseEntity.ok(HttpStatus.OK);
    }



    @PostMapping("user/{id}/friends/{newFriendId}")
    ResponseEntity<HttpStatus> addFriend(@PathVariable("id") Long id, @PathVariable("newFriendId") Long newFriendId ){
        friendshipService.addFriend(id, newFriendId);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @GetMapping("/info")
    public UserDto userData(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return new UserDto(user.getId(), user.getUsername(),user.getEmail());
    }

    @GetMapping("/{id}/info")
    public UserDto userInfo(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return new UserDto(user.getId(), user.getUsername(),user.getEmail());
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(FriendshipNotCreated e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotExist e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

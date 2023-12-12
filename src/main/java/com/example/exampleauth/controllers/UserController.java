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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/")
public class UserController {


    private final UserService userService;
    private final FriendshipService friendshipService;



    @GetMapping("/friends")
    ResponseEntity<Map<String, Long>> getFriends(){
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return new ResponseEntity<>(friendshipService.getUserFriends(userId), HttpStatus.OK);
    }

    @DeleteMapping("/friends/{exFriendId}")
    ResponseEntity<HttpStatus> deleteFriend( @PathVariable("exFriendId") Long exFriendId ){
        friendshipService.deleteFriend(exFriendId);
        return ResponseEntity.ok(HttpStatus.OK);
    }



    @PostMapping("/friends/{newFriendId}")
    ResponseEntity<HttpStatus> addFriend( @PathVariable("newFriendId") Long newFriendId ){
        friendshipService.addFriend(newFriendId);
        return ResponseEntity.ok(HttpStatus.OK);

    }


    @GetMapping("/info")
    public UserDto userInfo() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        User user = userService.getUserById(userId);
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

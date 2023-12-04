package com.example.exampleauth.controllers;

import com.example.exampleauth.config.exceptions.ErrorResponse;
import com.example.exampleauth.config.exceptions.RoleNotExist;
import com.example.exampleauth.config.exceptions.UserNotExist;
import com.example.exampleauth.enities.Role;
import com.example.exampleauth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/")
public class AdminController {

    private final UserService userService;

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotExist e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(RoleNotExist e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}/roles")
    ResponseEntity<HttpStatus> addRoleToUser(@PathVariable("id") Long id, @RequestParam(name = "roleId") Long roleId){
        userService.addRoleToUser(id, roleId);
        return ResponseEntity.ok(HttpStatus.OK);
    }



}

package com.astro.controller;

import com.astro.dto.workflow.UserDto;
import com.astro.service.UserService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDto userDto)  {
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(userService.login(userDto)), HttpStatus.OK);
    }

}

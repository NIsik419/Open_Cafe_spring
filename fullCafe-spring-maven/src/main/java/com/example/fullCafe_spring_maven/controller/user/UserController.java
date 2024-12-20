package com.example.fullCafe_spring_maven.controller.user;

import com.example.fullCafe_spring_maven.model.dto.user.RequestCreateUserDto;
import com.example.fullCafe_spring_maven.model.dto.user.ResponseSimpleUserDto;
import com.example.fullCafe_spring_maven.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "User",description = "Controller with User")
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    @Operation(summary = "retrieve",description = "retrieve user")
    public ResponseSimpleUserDto retrieveUser(Authentication auth){
        String uid = (String)auth.getPrincipal();
        return userService.findSimpleUserByUid(uid);
    }

    @PostMapping("/register")
    @Operation(summary = "create", description = "create user")
    public ResponseEntity<RequestCreateUserDto> createUser(@Valid @RequestBody RequestCreateUserDto user, Authentication auth){
        user.setEmail(auth.getName());
        user.setUid((String)auth.getPrincipal());
        userService.createUser(user);
        return new ResponseEntity<RequestCreateUserDto>(user, HttpStatus.CREATED);
    }
}

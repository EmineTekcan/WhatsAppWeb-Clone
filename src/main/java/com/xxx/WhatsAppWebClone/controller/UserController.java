package com.xxx.WhatsAppWebClone.controller;

import com.xxx.WhatsAppWebClone.model.User;
import com.xxx.WhatsAppWebClone.request.UpdateUserRequest;
import com.xxx.WhatsAppWebClone.response.ApiResponse;
import com.xxx.WhatsAppWebClone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String token)
            throws Exception{
        User user=userService.findUserProfile(token);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchUser(@PathVariable("query") String query){
        List<User> users=userService.searchUser(query);

        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser
            (@RequestBody UpdateUserRequest userRequest,
             @RequestHeader("Authorization") String token) throws Exception{
        User user=userService.findUserProfile(token);
        userService.updateUser(user.getId(),userRequest);
        ApiResponse apiResponse=ApiResponse.builder()
                .message("User updated successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }



}

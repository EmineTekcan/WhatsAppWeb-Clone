package com.xxx.WhatsAppWebClone.service;

import com.xxx.WhatsAppWebClone.exception.UserException;
import com.xxx.WhatsAppWebClone.model.User;
import com.xxx.WhatsAppWebClone.request.UpdateUserRequest;

import java.util.List;

public interface UserService {

    public User findUserById(Integer id) throws UserException;
    public User findUserProfile(String jwt) throws UserException;
    public User updateUser(Integer userId, UpdateUserRequest request) throws UserException;
    public List<User> searchUser(String query);

}

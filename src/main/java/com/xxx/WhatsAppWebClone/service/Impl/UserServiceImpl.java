package com.xxx.WhatsAppWebClone.service.Impl;

import com.xxx.WhatsAppWebClone.config.TokenProvider;
import com.xxx.WhatsAppWebClone.exception.UserException;
import com.xxx.WhatsAppWebClone.model.User;
import com.xxx.WhatsAppWebClone.repository.UserRepository;
import com.xxx.WhatsAppWebClone.request.UpdateUserRequest;
import com.xxx.WhatsAppWebClone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> user=userRepository.findById(id);

        if(user.isPresent())
            return user.get();
        throw new UserException("User not found with id "+id);
    }

    @Override
    public User findUserProfile(String jwt) throws UserException {
        String email=tokenProvider.getEmailFromToken(jwt);

        if (email ==null){
            throw new BadCredentialsException("Received invalid token---");
        }

        User user=userRepository.findByEmail(email);

        if (user==null){
            throw new UserException("User Not found with email: "+email);
        }

        return user;
    }

    @Override
    public User updateUser(Integer userId, UpdateUserRequest request) throws UserException {
       User user=findUserById(userId);

       if (request.getFull_name() !=null){
           user.setFull_name(request.getFull_name());
       }

       if(request.getProfile_picture() !=null){
           user.setProfile_picture(request.getProfile_picture());
       }
       return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
        List<User> users=userRepository.searchUser(query);
        return users;
    }
}

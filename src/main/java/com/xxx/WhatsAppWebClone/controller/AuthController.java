package com.xxx.WhatsAppWebClone.controller;

import com.xxx.WhatsAppWebClone.config.TokenProvider;
import com.xxx.WhatsAppWebClone.exception.UserException;
import com.xxx.WhatsAppWebClone.model.User;
import com.xxx.WhatsAppWebClone.repository.UserRepository;
import com.xxx.WhatsAppWebClone.request.LoginRequest;
import com.xxx.WhatsAppWebClone.request.UserRequest;
import com.xxx.WhatsAppWebClone.response.AuthResponse;
import com.xxx.WhatsAppWebClone.service.Impl.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final CustomUserService customUserService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody UserRequest user) throws UserException {
        String email= user.getEmail();
        String password= user.getPassword();
        String full_name=user.getFull_name();
        User findedUser=userRepository.findByEmail(email);

        if (findedUser !=null){
            throw new UserException("Email is used with another account "+email);
        }

        User createUser=new User();
        createUser.setEmail(email);
        createUser.setPassword(password);
        createUser.setFull_name(full_name);
        userRepository.save(createUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=tokenProvider.generateToken(authentication);
        AuthResponse authResponse=AuthResponse.builder()
                .jwt(jwt)
                .isAuth(true)
                .build();

        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest request){

        String email= request.getEmail();
        String password= request.getPassword();

       Authentication authentication=authenticate(email,password);
       SecurityContextHolder.getContext().setAuthentication(authentication);

       String jwt=tokenProvider.generateToken(authentication);

       AuthResponse authResponse=AuthResponse.builder()
               .jwt(jwt)
               .isAuth(true)
               .build();
        return new ResponseEntity<>(authResponse,HttpStatus.ACCEPTED);
    }

    public Authentication authenticate(String email, String password){
        UserDetails userDetails=customUserService.loadUserByUsername(email);

        if (userDetails ==null){
            throw new BadCredentialsException("Invalid user email");
        }
        String passwordd= userDetails.getPassword();

        System.out.println(passwordd + password);
        if (!password.equals(passwordd)){
            throw new BadCredentialsException("Invalid password or email");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }
}

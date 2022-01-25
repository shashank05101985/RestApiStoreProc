package com.common.auth.controller;

import com.common.auth.dto.LoginRequest;
import com.common.auth.dto.MessageResponse;
import com.common.auth.dto.SignUpRequest;
import com.common.auth.dto.UserInfoResponse;
import com.common.auth.security.JwtUtils;
import com.common.auth.service.AuthUser;
import com.common.auth.service.SessionServiceImpl;
import com.common.constants.CommonConstants;
import com.common.module.user.dao.UserDAO;
import com.common.module.user.dto.User;
import com.common.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthUser userDetails = (AuthUser) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails.getUsername());
        userDetails.setToken(jwtToken);
        sessionService.addAuthMap(userDetails);
        Collection<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles, jwtToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userDAO.getByEmail(signUpRequest.getEmail()) != null) {
            return ResponseEntity.ok().body(new MessageResponse("Error: Email is already in use!", CommonConstants.MessageType.ERROR));
        }
        int id = userService.saveUser(signUpRequest);
        if (id > 0)
            return ResponseEntity.ok(new MessageResponse("User registered successfully!",CommonConstants.MessageType.SUCCESS));
        else
            return ResponseEntity.ok(new MessageResponse("Some error occurred!",CommonConstants.MessageType.ERROR));

    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
       /* try {
            AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            sessionService.removeAuthMap(authUser);
        }catch (Exception e)
        {

        }*/
        return ResponseEntity.ok()
                .body(new MessageResponse("You've been signed out!",CommonConstants.MessageType.SUCCESS));
    }
}

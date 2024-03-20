package com.kibo.survey.WebAPI.controllers;

import com.kibo.survey.business.abstracts.UserService;
import com.kibo.survey.core.security.JwtService;
import com.kibo.survey.core.utilities.result.DataResult;
import com.kibo.survey.core.utilities.result.ErrorDataResult;
import com.kibo.survey.core.utilities.result.Result;
import com.kibo.survey.core.utilities.result.SuccessDataResult;
import com.kibo.survey.entities.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/generateToken")
    public DataResult<String> generateToken(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {
            var token = jwtService.generateToken(username);

            var cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setDomain("localhost");
            cookie.setMaxAge(31536000);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);

            response.addCookie(cookie);

            return new SuccessDataResult<String>(token, "Token generated successfully");
        }
        return new ErrorDataResult<>("Invalid username or password");
    }

    @PostMapping("/registerAdmin")
    public Result registerUser(@RequestBody User user){
        return userService.addUser(user);
    }


}

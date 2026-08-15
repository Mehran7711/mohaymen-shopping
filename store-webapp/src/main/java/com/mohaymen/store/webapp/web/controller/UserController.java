package com.mohaymen.store.webapp.web.controller;

import com.mohaymen.store.webapp.service.IUserService;
import com.mohaymen.store.webapp.web.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: Mehran
 * Created on: 8/8/26
 */

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/current-user")
    @ResponseBody
    public UserResponse currentUser(Authentication authentication) {
        return userService.getCurrentUser(authentication);
    }
}

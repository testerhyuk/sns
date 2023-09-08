package com.socialnetwork.sns.controller;

import com.socialnetwork.sns.controller.request.UserJoinRequest;
import com.socialnetwork.sns.controller.request.UserLoginRequest;
import com.socialnetwork.sns.controller.response.Response;
import com.socialnetwork.sns.controller.response.UserJoinResponse;
import com.socialnetwork.sns.controller.response.UserLoginResponse;
import com.socialnetwork.sns.model.User;
import com.socialnetwork.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.getName(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
}
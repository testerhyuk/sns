package com.socialnetwork.sns.controller;

import com.socialnetwork.sns.controller.request.UserJoinRequest;
import com.socialnetwork.sns.controller.request.UserLoginRequest;
import com.socialnetwork.sns.controller.response.AlarmResponse;
import com.socialnetwork.sns.controller.response.Response;
import com.socialnetwork.sns.controller.response.UserJoinResponse;
import com.socialnetwork.sns.controller.response.UserLoginResponse;
import com.socialnetwork.sns.exception.ErrorCode;
import com.socialnetwork.sns.exception.SnsApplicationException;
import com.socialnetwork.sns.model.User;
import com.socialnetwork.sns.service.UserService;
import com.socialnetwork.sns.utils.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal()
                , User.class).orElseThrow(() -> new SnsApplicationException(ErrorCode.INTERNAL_SERVER_ERROR,
                "Casting to User class failed"));
        return Response.success(userService.alarmList(user.getId(), pageable).map(AlarmResponse::fromAlarm));
    }
}

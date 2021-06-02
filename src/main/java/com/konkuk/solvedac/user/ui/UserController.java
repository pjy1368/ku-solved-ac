package com.konkuk.solvedac.user.ui;

import com.konkuk.solvedac.api.application.UserInfoProvider;
import com.konkuk.solvedac.user.dto.UserInfoResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserInfoProvider userInfoProvider;

    public UserController(UserInfoProvider userInfoProvider) {
        this.userInfoProvider = userInfoProvider;
    }

    @GetMapping("/users")
    public ResponseEntity<UserInfoResponses> showUserInfosInGroup(@RequestParam String groupId) {
        return ResponseEntity.ok(userInfoProvider.getUserInfosInGroup(groupId));
    }
}

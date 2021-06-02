package com.konkuk.solvedac.user.ui;

import com.konkuk.solvedac.api.application.UserInfoProvider;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.user.application.UserService;
import com.konkuk.solvedac.user.dto.UserInfoResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserInfoProvider userInfoProvider;
    private final UserService userService;

    public UserController(UserInfoProvider userInfoProvider, UserService userService) {
        this.userInfoProvider = userInfoProvider;
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<UserInfoResponses> showUserInfosInGroup(@RequestParam String groupId) {
        return ResponseEntity.ok(userInfoProvider.getUserInfosInGroup(groupId));
    }

    @PostMapping("/users/solved-problems")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblemsOfUsers(@RequestBody String groupId) {
        final UserInfoResponses userInfosInGroup = userInfoProvider.getUserInfosInGroup(groupId);
        return ResponseEntity.ok(userService.showSolvedProblemsOfUsers(userInfosInGroup));
    }
}

package com.konkuk.solvedac.user.ui;

import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.api.application.UserInfoProvider;
import com.konkuk.solvedac.problem.application.ProblemService;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.user.application.UserService;
import com.konkuk.solvedac.user.dto.UserInfoResponses;
import org.springframework.http.MediaType;
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

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoResponses> showUserInfosInGroup(@RequestParam Long groupId) {
        if (!userService.isSavedUserInGroup(groupId)) {
            final UserInfoResponses userInfosInGroup = userInfoProvider.getUserInfosInGroup(groupId);
            userService.saveUsers(groupId, userInfosInGroup);
        }
        return ResponseEntity.ok(userService.findByGroupId(groupId));
    }

    @PostMapping("/users/solved-problems")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblemsOfUsers(@RequestBody Long groupId) {
        return ResponseEntity.ok(userService.showSolvedProblemsOfUsers(groupId));
    }

    @PostMapping("/users/unsolved-problems")
    public ResponseEntity<ProblemInfoResponses> showUnsolvedProblemsOfUsers(@RequestBody Long groupId) {
        return ResponseEntity.ok(userService.showUnsolvedProblemsOfUsers(groupId));
    }
}

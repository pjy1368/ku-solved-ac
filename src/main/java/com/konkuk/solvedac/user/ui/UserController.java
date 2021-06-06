package com.konkuk.solvedac.user.ui;

import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.api.application.UserInfoProvider;
import com.konkuk.solvedac.problem.application.ProblemService;
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
        if (!userService.isSavedUserInGroup(Long.parseLong(groupId))) {
            final UserInfoResponses userInfosInGroup = userInfoProvider.getUserInfosInGroup(Long.parseLong(groupId));
            userService.saveUsers(Long.parseLong(groupId), userInfosInGroup);
        }
        return ResponseEntity.ok(userService.findByGroupId(Long.parseLong(groupId)));
    }

    @PostMapping("/users/solved-problems")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblemsOfUsers(@RequestBody String groupId) {
        return ResponseEntity.ok(userService.showSolvedProblemsOfUsers(Long.parseLong(groupId)));
    }

    @PostMapping("/users/unsolved-problems")
    public ResponseEntity<ProblemInfoResponses> showUnsolvedProblemsOfUsers(@RequestBody String groupId) {
        return ResponseEntity.ok(userService.showUnsolvedProblemsOfUsers(Long.parseLong(groupId)));
    }
}

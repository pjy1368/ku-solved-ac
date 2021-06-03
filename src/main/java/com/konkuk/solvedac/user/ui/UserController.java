package com.konkuk.solvedac.user.ui;

import com.konkuk.solvedac.api.application.ProblemsProvider;
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
    private final ProblemsProvider problemsProvider;
    private final UserService userService;

    public UserController(UserInfoProvider userInfoProvider, ProblemsProvider problemsProvider, UserService userService) {
        this.userInfoProvider = userInfoProvider;
        this.problemsProvider = problemsProvider;
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<UserInfoResponses> showUserInfosInGroup(@RequestParam Long groupId) {
        final UserInfoResponses userInfosInGroup = userInfoProvider.getUserInfosInGroup(groupId);
        userService.saveUsers(groupId, userInfosInGroup);
        return ResponseEntity.ok(userService.findByGroupId(groupId));
    }

    @PostMapping("/users/solved-problems")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblemsOfUsers(@RequestBody Long groupId) {
        final UserInfoResponses userInfosInGroup = userInfoProvider.getUserInfosInGroup(groupId);
        return ResponseEntity.ok(userService.showSolvedProblemsOfUsers(userInfosInGroup));
    }

    @PostMapping("/users/unsolved-problems")
    public ResponseEntity<ProblemInfoResponses> showUnsolvedProblemsOfUsers(@RequestBody Long groupId) {
        final ProblemInfoResponses allProblemResponse = problemsProvider.getAllProblems();
        final UserInfoResponses userInfosInGroup = userInfoProvider.getUserInfosInGroup(groupId);
        final ProblemInfoResponses solvedProblemResponse = userService.showSolvedProblemsOfUsers(userInfosInGroup);
        return ResponseEntity.ok(userService.showUnsolvedProblemsOfUsers(allProblemResponse, solvedProblemResponse));
    }
}

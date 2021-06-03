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
    public ResponseEntity<UserInfoResponses> showUserInfosInGroup(@RequestParam String groupId) {
        final UserInfoResponses userInfosInGroup = userInfoProvider.getUserInfosInGroup(groupId);
        userService.saveUsers(Long.parseLong(groupId), userInfosInGroup);
        return ResponseEntity.ok(userService.findByGroupId(Long.parseLong(groupId)));
    }

    @PostMapping("/users/solved-problems")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblemsOfUsers(@RequestBody String groupId) {
        final UserInfoResponses userInfoResponses = userService.findByGroupId(Long.parseLong(groupId));
        return ResponseEntity.ok(userService.showSolvedProblemsOfUsers(userInfoResponses));
    }

    @PostMapping("/users/unsolved-problems")
    public ResponseEntity<ProblemInfoResponses> showUnsolvedProblemsOfUsers(@RequestBody String  groupId) {
        final ProblemInfoResponses allProblemResponse = problemsProvider.getAllProblems();
        final UserInfoResponses userInfosInGroup = userInfoProvider.getUserInfosInGroup(groupId);
        final ProblemInfoResponses solvedProblemResponse = userService.showSolvedProblemsOfUsers(userInfosInGroup);
        return ResponseEntity.ok(userService.showUnsolvedProblemsOfUsers(allProblemResponse, solvedProblemResponse));
    }
}

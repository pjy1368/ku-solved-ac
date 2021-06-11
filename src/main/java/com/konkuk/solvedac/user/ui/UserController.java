package com.konkuk.solvedac.user.ui;

import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.problem.dto.UserId;
import com.konkuk.solvedac.user.application.UserService;
import com.konkuk.solvedac.user.dto.UserInfoResponses;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<UserInfoResponses> showUserInfosInGroup(@RequestParam Long groupId) {
        return ResponseEntity.ok(userService.findByGroupId(groupId));
    }

    @PostMapping(value = "/users/problems")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblems(@Valid @RequestBody UserId userId) {
        return ResponseEntity.ok(userService.findSolvedProblemByUserId(userId.getId()));
    }

    @PostMapping("/users/solved-problems")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblemsOfUsers(@RequestBody Long groupId) {
        return ResponseEntity.ok(userService.showSolvedProblemsOfUsers(groupId));
    }

    @PostMapping("/users/unsolved-problems")
    public ResponseEntity<ProblemInfoResponses> showUnsolvedProblemsOfUsers(@RequestBody Long groupId) {
        return ResponseEntity.ok(userService.showUnsolvedProblemsOfUsers(groupId));
    }

    @PostMapping("/users/unsolved-problems/{tier}")
    public ResponseEntity<ProblemInfoResponses> showUnsolvedProblemsOfUsersByTier(
        @RequestBody Long groupId, @PathVariable String tier) {
        return ResponseEntity.ok(userService.showUnsolvedProblemsOfUsersByTier(groupId, tier));
    }
}

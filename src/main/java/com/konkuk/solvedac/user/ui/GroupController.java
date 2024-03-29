package com.konkuk.solvedac.user.ui;

import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groups/{id}")
public class GroupController {

    private final UserService userService;

    @GetMapping("/solved-problems")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblemsOfUsers(@PathVariable("id") Integer groupId) {
        return ResponseEntity.ok(userService.showSolvedProblemsOfUsers(groupId));
    }

    @GetMapping("/solved-problems/{tier}")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblemsOfUsersByTier(
        @PathVariable("id") Integer groupId, @PathVariable String tier) {
        return ResponseEntity.ok(userService.showSolvedProblemsOfUsersByTier(groupId, tier));
    }

    @GetMapping("/unsolved-problems")
    public ResponseEntity<ProblemInfoResponses> showUnsolvedProblemsOfUsers(@PathVariable("id") Integer groupId) {
        return ResponseEntity.ok(userService.showUnsolvedProblemsOfUsers(groupId));
    }

    @GetMapping("/unsolved-problems/{tier}")
    public ResponseEntity<ProblemInfoResponses> showUnsolvedProblemsOfUsersByTier(
        @PathVariable("id") Integer groupId, @PathVariable String tier) {
        return ResponseEntity.ok(userService.showUnsolvedProblemsOfUsersByTier(groupId, tier));
    }
}

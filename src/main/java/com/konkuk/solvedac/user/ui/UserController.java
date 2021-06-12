package com.konkuk.solvedac.user.ui;

import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.user.application.UserService;
import com.konkuk.solvedac.user.dto.UserInfoResponses;
import javax.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserInfoResponses> showUserInfosInGroup(@RequestParam("group_id") Long groupId) {
        return ResponseEntity.ok(userService.findByGroupId(groupId));
    }

    @GetMapping("/{id}/problems")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblems(@PathVariable
    @NotBlank(message = "유저의 아이디는 1글자 이상이어야 합니다.") String id) {
        return ResponseEntity.ok(userService.findSolvedProblemByUserId(id));
    }

    @GetMapping("/{id}/problems/{tier}")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblemsByTier(@PathVariable
    @NotBlank(message = "유저의 아이디는 1글자 이상이어야 합니다.") String id, @PathVariable String tier) {
        return ResponseEntity.ok(userService.findSolvedProblemByUserIdAndTier(id, tier));
    }
}

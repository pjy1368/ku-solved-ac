package com.konkuk.solvedac.problem.ui;

import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProblemController {

    private final ProblemsProvider problemsProvider;

    public ProblemController(ProblemsProvider problemsProvider) {
        this.problemsProvider = problemsProvider;
    }

    @GetMapping("/problems")
    public ResponseEntity<ProblemInfoResponses> showAllProblems() {
        return ResponseEntity.ok(problemsProvider.getAllProblems());
    }

    @PostMapping("/problems")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblems(@RequestBody String userId) {
        return ResponseEntity.ok(problemsProvider.getSolvedProblems(userId));
    }
}

package com.konkuk.solvedac.problem.ui;

import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.problem.application.ProblemService;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProblemController {

    private final ProblemsProvider problemsProvider;
    private final ProblemService problemService;

    public ProblemController(ProblemsProvider problemsProvider, ProblemService problemService) {
        this.problemsProvider = problemsProvider;
        this.problemService = problemService;
    }

    @GetMapping("/problems")
    public ResponseEntity<ProblemInfoResponses> showAllProblems() {
        final ProblemInfoResponses allProblems = problemsProvider.getAllProblems();
        problemService.saveProblems(allProblems);
        return ResponseEntity.ok(problemService.findAll());
    }

    @GetMapping("/problems2")
    public ResponseEntity<ProblemInfoResponses> showAllProblems2() {
        return ResponseEntity.ok(problemService.findAll());
    }

    @PostMapping("/problems")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblems(@RequestBody String userId) {
        final ProblemInfoResponses solvedProblems = problemsProvider.getSolvedProblems(userId);
        problemService.saveProblems(userId, solvedProblems);
        return ResponseEntity.ok(problemService.findByUserId(userId));
    }

    @PostMapping("/problems2")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblems2(@RequestBody String userId) {
        return ResponseEntity.ok(problemService.findByUserId(userId));
    }
}

package com.konkuk.solvedac.problem.ui;

import com.konkuk.solvedac.problem.application.ProblemService;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping
    public ResponseEntity<ProblemInfoResponses> showAllProblems() {
        return ResponseEntity.ok(problemService.findAllProblems());
    }

    @GetMapping("/{tier}")
    public ResponseEntity<ProblemInfoResponses> showProblemsByTier(@PathVariable String tier) {
        return ResponseEntity.ok(problemService.findProblemByTier(tier));
    }
}

package com.konkuk.solvedac.problem.ui;

import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.problem.application.ProblemService;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.problem.dto.UserId;
import javax.validation.Valid;
import org.springframework.http.MediaType;
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

    @GetMapping(value = "/problems", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProblemInfoResponses> showAllProblems() {
        return ResponseEntity.ok(problemService.findAllProblems());
    }

    @PostMapping(value = "/problems")
    public ResponseEntity<ProblemInfoResponses> showSolvedProblems(@Valid @RequestBody UserId userId) {
        final String id = userId.getId();
        if (!problemService.isAlreadyMappedUserProblems(id)) {
            final ProblemInfoResponses solvedProblems = problemsProvider.getSolvedProblems(id);
            problemService.saveProblems(id, solvedProblems);
        }
        return ResponseEntity.ok(problemService.findByUserId(id));
    }
}

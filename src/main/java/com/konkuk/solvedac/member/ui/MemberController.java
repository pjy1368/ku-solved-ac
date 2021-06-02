package com.konkuk.solvedac.member.ui;

import com.konkuk.solvedac.member.api.application.SolvedProblemsProvider;
import com.konkuk.solvedac.member.dto.ProblemResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final SolvedProblemsProvider solvedProblemsProvider;

    public MemberController(SolvedProblemsProvider solvedProblemsProvider) {
        this.solvedProblemsProvider = solvedProblemsProvider;
    }

    @GetMapping("/members/problems")
    public ResponseEntity<ProblemResponses> showSolvedProblems(@RequestParam String id) {
        return ResponseEntity.ok(solvedProblemsProvider.getSolvedProblems(id));
    }
}

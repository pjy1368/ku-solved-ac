package com.konkuk.solvedac.member.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SolvedProblemsResponse {

    private boolean success;
    private SolvedProblemsResultResponse result;

    public Long getTotalPage() {
        return result.getTotalPage();
    }

    public List<ProblemResponse> getProblems() {
        return result.getProblems();
    }
}

package com.konkuk.solvedac.member.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SolvedProblemsResultResponse {

    private Long totalProblems;
    private Long totalPage;
    private List<ProblemResponse> problems;
}

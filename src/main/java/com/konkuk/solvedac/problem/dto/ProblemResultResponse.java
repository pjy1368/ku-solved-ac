package com.konkuk.solvedac.problem.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemResultResponse {

    private Long totalProblems;
    private Long totalPage;
    private List<ProblemInfoResponse> problems;
}

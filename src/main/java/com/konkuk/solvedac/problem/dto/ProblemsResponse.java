package com.konkuk.solvedac.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemsResponse {

    private boolean success;
    private ProblemResultResponse result;
}

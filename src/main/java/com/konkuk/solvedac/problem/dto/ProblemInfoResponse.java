package com.konkuk.solvedac.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemInfoResponse {

    private Long id;
    private Long level;
    private Short levelLocked;
    private Short solvable;
    private String title;
    private Long solvedCount;
    private Double averageTry;
}

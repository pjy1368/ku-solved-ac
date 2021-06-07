package com.konkuk.solvedac.problem.dto;

import com.konkuk.solvedac.problem.domain.Problem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ProblemInfoResponse {

    @NonNull
    private Long id;
    @NonNull
    private Integer level;
    private Short levelLocked;
    private Short solvable;
    @NonNull
    private String title;
    @NonNull
    private Long solvedCount;
    private Double averageTry;

    public static ProblemInfoResponse of(Problem problem) {
        return new ProblemInfoResponse(problem.getId(), problem.getLevel(), problem.getTitle(), problem.getSolvedCount());
    }

    public Problem toEntity() {
        return new Problem(id, level, title, solvedCount);
    }
}

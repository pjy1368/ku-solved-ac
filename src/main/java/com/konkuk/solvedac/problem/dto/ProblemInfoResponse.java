package com.konkuk.solvedac.problem.dto;

import com.konkuk.solvedac.problem.domain.Problem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProblemInfoResponse {

    private Long id;
    private Long level;
    private Short levelLocked;
    private Short solvable;
    private String title;
    private Long solvedCount;
    private Double averageTry;

    public ProblemInfoResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static ProblemInfoResponse of(Problem problem) {
        return new ProblemInfoResponse(problem.getId(), problem.getTitle());
    }

    public Problem toEntity() {
        return new Problem(id, title);
    }
}

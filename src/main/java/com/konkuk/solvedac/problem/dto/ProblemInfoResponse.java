package com.konkuk.solvedac.problem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.konkuk.solvedac.problem.domain.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemInfoResponse {

    private Integer problemId;

    @JsonProperty("titleKo")
    private String title;

    private Boolean isSolvable;

    private Boolean isPartial;

    @JsonProperty("acceptedUserCount")
    private Integer solvedCount;

    private Integer level;

    private Integer votedUserCount;

    private Boolean isLevelLocked;

    private Double averageTries;

    public static ProblemInfoResponse of(Problem problem) {
        return builder()
            .problemId(problem.getId())
            .level(problem.getLevel())
            .title(problem.getTitle())
            .solvedCount(problem.getSolvedCount())
            .build();
    }

    public Problem toEntity() {
        return Problem.builder()
            .id(problemId)
            .level(level)
            .solvedCount(solvedCount)
            .title(title)
            .build();
    }
}

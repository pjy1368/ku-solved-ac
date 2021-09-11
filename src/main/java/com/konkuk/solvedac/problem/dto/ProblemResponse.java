package com.konkuk.solvedac.problem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemResponse {

    private Integer totalPages;

    @JsonProperty("count")
    private Integer totalProblems;

    @JsonProperty("items")
    private List<ProblemInfoResponse> problemInfoResponses;
}

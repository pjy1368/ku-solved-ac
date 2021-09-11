package com.konkuk.solvedac.problem.dto;

import com.konkuk.solvedac.problem.domain.Problem;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemInfoResponses {

    List<ProblemInfoResponse> problemInfoResponses;

    public static ProblemInfoResponses of(Set<Problem> problems) {
        return new ProblemInfoResponses(problems.stream()
            .map(ProblemInfoResponse::of)
            .collect(Collectors.toList())
        );
    }
}

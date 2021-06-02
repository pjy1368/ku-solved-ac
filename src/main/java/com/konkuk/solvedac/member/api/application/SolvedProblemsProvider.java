package com.konkuk.solvedac.member.api.application;

import static com.konkuk.solvedac.member.api.application.Constants.SERVER_URL;

import com.konkuk.solvedac.member.dto.ProblemResponse;
import com.konkuk.solvedac.member.dto.ProblemResponses;
import com.konkuk.solvedac.member.dto.SolvedProblemsResponse;
import com.konkuk.solvedac.member.dto.SolvedProblemsResultResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SolvedProblemsProvider {

    private final RestTemplate template;

    public SolvedProblemsProvider(RestTemplateBuilder restTemplateBuilder) {
        this.template = restTemplateBuilder.build();
    }

    public ProblemResponses getSolvedProblems(String id) {
        final String url = SERVER_URL + "/v2/search/problems.json?query=solved_by:" + id;
        final SolvedProblemsResponse initialSolvedProblemsResponse = template.getForObject(url, SolvedProblemsResponse.class);
        final SolvedProblemsResultResponse result = Objects.requireNonNull(initialSolvedProblemsResponse).getResult();

        final long totalPages = Objects.requireNonNull(result.getTotalPage());
        final List<ProblemResponse> problemResponses = new ArrayList<>(result.getProblems());

        for (int i = 2; i <= totalPages; i++) {
            problemResponses.addAll(Objects.requireNonNull(template.getForObject(url + "&page=" + i,
                SolvedProblemsResponse.class)).getResult().getProblems());
        }
        return new ProblemResponses(problemResponses);
    }
}

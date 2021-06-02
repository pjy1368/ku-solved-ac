package com.konkuk.solvedac.api.application;

import static com.konkuk.solvedac.api.Constants.ALL_PROBLEMS_URL;
import static com.konkuk.solvedac.api.Constants.PER_PAGE_URL;
import static com.konkuk.solvedac.api.Constants.SERVER_URL;
import static com.konkuk.solvedac.api.Constants.SOLVED_PROBLEMS_URL;

import com.konkuk.solvedac.problem.dto.ProblemInfoResponse;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.problem.dto.ProblemResultResponse;
import com.konkuk.solvedac.problem.dto.ProblemsResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProblemsProvider {

    private final RestTemplate template;

    public ProblemsProvider(RestTemplateBuilder restTemplateBuilder) {
        this.template = restTemplateBuilder.build();
    }

    public ProblemInfoResponses getSolvedProblems(String id) {
        final String url = SERVER_URL + SOLVED_PROBLEMS_URL + id;
        return requestProblems(url);
    }

    public ProblemInfoResponses getAllProblems() {
        final String url = SERVER_URL + ALL_PROBLEMS_URL;
        return requestProblems(url);
    }

    private ProblemInfoResponses requestProblems(String url) {
        final ProblemsResponse initialProblemsResponse = template.getForObject(url, ProblemsResponse.class);
        final ProblemResultResponse result = Objects.requireNonNull(initialProblemsResponse).getResult();

        final long totalPages = Objects.requireNonNull(result.getTotalPage());
        final List<ProblemInfoResponse> problemInfoResponses = new ArrayList<>(result.getProblems());

        for (int i = 2; i <= totalPages; i++) {
            problemInfoResponses.addAll(Objects.requireNonNull(template.getForObject(url + PER_PAGE_URL + i,
                ProblemsResponse.class)).getResult().getProblems());
        }
        return new ProblemInfoResponses(problemInfoResponses);
    }
}

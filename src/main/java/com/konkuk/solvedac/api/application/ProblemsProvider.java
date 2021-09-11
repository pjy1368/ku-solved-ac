package com.konkuk.solvedac.api.application;

import static com.konkuk.solvedac.api.Constants.ALL_PROBLEMS_QUERY_V3;
import static com.konkuk.solvedac.api.Constants.PER_PAGE_URL;
import static com.konkuk.solvedac.api.Constants.PROBLEMS_URL_V3;
import static com.konkuk.solvedac.api.Constants.SERVER_URL_V3;
import static com.konkuk.solvedac.api.Constants.SOLVED_PROBLEMS_URL_V3;

import com.konkuk.solvedac.problem.dto.ProblemInfoResponse;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.problem.dto.ProblemResponse;
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

    public ProblemInfoResponses getSolvedProblems(String userId) {
        final String url = SERVER_URL_V3 + SOLVED_PROBLEMS_URL_V3 + userId;
        return requestProblems(url);
    }

    public ProblemInfoResponses getAllProblems() {
        final String url = SERVER_URL_V3 + PROBLEMS_URL_V3 + ALL_PROBLEMS_QUERY_V3;
        return requestProblems(url);
    }

    private ProblemInfoResponses requestProblems(String url) {
        ProblemResponse problemResponse = template.getForObject(url, ProblemResponse.class);
        problemResponse.setTotalPages((int) Math.ceil((problemResponse.getTotalProblems() / 100.0)));

        final int totalPages = problemResponse.getTotalPages();
        final List<ProblemInfoResponse> problemInfoResponses = new ArrayList<>(problemResponse.getProblemInfoResponses());

        for (int i = 2; i <= totalPages; i++) {
            problemInfoResponses.addAll(Objects.requireNonNull(template.getForObject(url + PER_PAGE_URL + i,
                ProblemResponse.class).getProblemInfoResponses()));
            try {
                Thread.sleep(340);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new ProblemInfoResponses(problemInfoResponses);
    }
}

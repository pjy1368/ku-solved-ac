package com.konkuk.solvedac.api.application;

import static com.konkuk.solvedac.api.Constants.ALL_PROBLEMS_QUERY;
import static com.konkuk.solvedac.api.Constants.DATA_COUNT_PER_PAGE;
import static com.konkuk.solvedac.api.Constants.PER_PAGE_URL;
import static com.konkuk.solvedac.api.Constants.PROBLEMS_URL;
import static com.konkuk.solvedac.api.Constants.SERVER_URL;
import static com.konkuk.solvedac.api.Constants.SOLVED_PROBLEMS_URL;

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
        final String url = SERVER_URL + SOLVED_PROBLEMS_URL + userId;
        return requestProblems(url);
    }

    public ProblemInfoResponses getAllProblems() {
        final String url = SERVER_URL + PROBLEMS_URL + ALL_PROBLEMS_QUERY;
        return requestProblems(url);
    }

    private ProblemInfoResponses requestProblems(String url) {
        try {
            final ProblemResponse problemResponse = template.getForObject(url, ProblemResponse.class);
            Thread.sleep(1200);

            final int totalPages = (int) Math.ceil((problemResponse.getTotalProblems() / DATA_COUNT_PER_PAGE));
            final List<ProblemInfoResponse> problemInfoResponses = new ArrayList<>(
                problemResponse.getProblemInfoResponses());

            for (int i = 2; i <= totalPages; i++) {
                problemInfoResponses.addAll(Objects.requireNonNull(template.getForObject(url + PER_PAGE_URL + i,
                    ProblemResponse.class).getProblemInfoResponses()));
                Thread.sleep(1200);
            }
            return new ProblemInfoResponses(problemInfoResponses);
        } catch (InterruptedException e) {
            throw new RuntimeException("API 요청 대기 시간 중 에러가 발생했습니다.");
        }
    }
}

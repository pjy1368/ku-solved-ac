package com.konkuk.solvedac.member.api.application;

import static com.konkuk.solvedac.member.api.application.Constants.SERVER_URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.konkuk.solvedac.member.api.InvalidIOException;
import com.konkuk.solvedac.member.dto.ProblemResponse;
import com.konkuk.solvedac.member.dto.ProblemResponses;
import com.konkuk.solvedac.member.dto.SolvedProblemsResponse;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class SolvedProblemsProvider {

    public ProblemResponses getSolvedProblems(String id) {
        final CloseableHttpClient client = HttpClientBuilder.create().build();
        final String url = SERVER_URL + "/v2/search/problems.json?query=solved_by:" + id;
        final HttpGet request = new HttpGet(url);
        final SolvedProblemsResponse initialSolvedProblemsResponse = requestSolvedProblemsOfMember(client, request);
        final Long totalPages = initialSolvedProblemsResponse.getTotalPage();
        final List<ProblemResponse> problemResponses = new ArrayList<>(initialSolvedProblemsResponse.getProblems());
        for (int i = 2; i <= totalPages; i++) {
            request.setURI(URI.create(url + "&page=" + i));
            final SolvedProblemsResponse solvedProblemsResponse = requestSolvedProblemsOfMember(client, request);
            problemResponses.addAll(solvedProblemsResponse.getProblems());
        }
        return new ProblemResponses(problemResponses);
    }

    private SolvedProblemsResponse requestSolvedProblemsOfMember(CloseableHttpClient client, HttpGet request) {
        try {
            final CloseableHttpResponse response = client.execute(request);
            final HttpEntity entity = response.getEntity();
            final ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            final String result = EntityUtils.toString(entity, "UTF-8");
            final SolvedProblemsResponse solvedProblemsResponse = mapper.readValue(result, SolvedProblemsResponse.class);
            if (!solvedProblemsResponse.isSuccess()) {
                throw new InvalidIOException("예상치 못한 에러로 멤버의 푼 문제 리스트를 가져오지 못하였습니다.");
            }
            return solvedProblemsResponse;
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidIOException("라이브러리 예외가 발생하였습니다.");
        }
    }
}

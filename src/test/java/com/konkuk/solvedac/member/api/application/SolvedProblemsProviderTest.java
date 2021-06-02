package com.konkuk.solvedac.member.api.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konkuk.solvedac.member.dto.ProblemResponse;
import com.konkuk.solvedac.member.dto.ProblemResponses;
import com.konkuk.solvedac.member.dto.SolvedProblemsResponse;
import com.konkuk.solvedac.member.dto.SolvedProblemsResultResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(SolvedProblemsProvider.class)
class SolvedProblemsProviderTest {

    @Autowired
    private SolvedProblemsProvider solvedProblemsProvider;

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper mapper;

    private final String orderApiUrl = "https://api.solved.ac" + "/v2/search/problems.json?query=solved_by:";

    @Test
    void getSolvedProblem() throws JsonProcessingException {
        final String expectedMemberId = "test";
        final List<ProblemResponse> problems = Collections.singletonList(
            new ProblemResponse(1000L, 1L, (short) 1, (short) 1, "A+B", 126343L, 2.2986)
        );
        final SolvedProblemsResultResponse result = new SolvedProblemsResultResponse(1L, 1L, problems);
        final SolvedProblemsResponse solvedProblemsResponse = new SolvedProblemsResponse(true, result);
        final String expectResult = mapper.writeValueAsString(solvedProblemsResponse);

        this.mockServer.expect(requestTo(orderApiUrl + expectedMemberId))
            .andRespond(withSuccess(expectResult, MediaType.APPLICATION_JSON));

        final ProblemResponses actual = solvedProblemsProvider.getSolvedProblems(expectedMemberId);
        assertThat(actual.getProblemResponses()).hasSize(1);
        assertThat(actual.getProblemResponses().get(0).getId()).isEqualTo(1000L);
    }

    @Test
    void getSolvedProblems() throws JsonProcessingException {
        final String expectedMemberId = "test2";
        final List<ProblemResponse> problems = Arrays.asList(
            new ProblemResponse(1000L, 1L, (short) 1, (short) 1, "A+B", 126343L, 2.2986),
            new ProblemResponse(1001L, 1L, (short) 1, (short) 1, "A+B+C", 12332L, 2.3)
        );
        final SolvedProblemsResultResponse result = new SolvedProblemsResultResponse(2L, 1L, problems);
        final SolvedProblemsResponse solvedProblemsResponse = new SolvedProblemsResponse(true, result);
        final String expectResult = mapper.writeValueAsString(solvedProblemsResponse);

        this.mockServer.expect(requestTo(orderApiUrl + expectedMemberId))
            .andRespond(withSuccess(expectResult, MediaType.APPLICATION_JSON));

        final ProblemResponses actual = solvedProblemsProvider.getSolvedProblems(expectedMemberId);
        assertThat(actual.getProblemResponses()).hasSize(2);
        assertThat(actual.getProblemResponses().get(0).getId()).isEqualTo(1000L);
        assertThat(actual.getProblemResponses().get(1).getId()).isEqualTo(1001L);
    }
}
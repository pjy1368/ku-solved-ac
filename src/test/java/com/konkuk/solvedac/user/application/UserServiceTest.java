package com.konkuk.solvedac.user.application;

import static com.konkuk.solvedac.api.Constants.SERVER_URL;
import static com.konkuk.solvedac.api.Constants.SOLVED_PROBLEMS_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponse;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.problem.dto.ProblemResultResponse;
import com.konkuk.solvedac.problem.dto.ProblemsResponse;
import com.konkuk.solvedac.user.dto.UserInfoResponse;
import com.konkuk.solvedac.user.dto.UserInfoResponses;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest({UserService.class, ProblemsProvider.class})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("유저가 푼 중복이 없는 문제 하나를 조회한다.")
    void showSolvedProblemOfUsers() throws JsonProcessingException {
        final UserInfoResponses userInfoResponses = new UserInfoResponses(
            Collections.singletonList(
                new UserInfoResponse("test", "", "test.test", 100L,
                    2000L, 3, 6, 4L, 0L, 4L, 4, 344)
            )
        );

        final List<ProblemInfoResponse> problems = Collections.singletonList(
            new ProblemInfoResponse(1000L, 1L, (short) 1, (short) 1, "A+B", 126343L, 2.2986)
        );
        final ProblemResultResponse result = new ProblemResultResponse(1L, 1L, problems);
        final ProblemsResponse problemsResponse = new ProblemsResponse(true, result);
        final String expectedResult = mapper.writeValueAsString(problemsResponse);

        this.mockServer.expect(requestTo(SERVER_URL + SOLVED_PROBLEMS_URL + "test"))
            .andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        ProblemInfoResponses actual = userService.showSolvedProblemsOfUsers(userInfoResponses);
        assertThat(actual.getProblemInfoResponses()).hasSize(1);
        assertThat(actual.getProblemInfoResponses().get(0).getId()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("유저가 푼 중복이 있는 문제 리스트를 조회한다.")
    void showSolvedProblemsOfUsers() throws JsonProcessingException {
        final UserInfoResponses userInfoResponses = new UserInfoResponses(
            Collections.singletonList(
                new UserInfoResponse("test", "", "test.test", 100L,
                    2000L, 3, 6, 4L, 0L, 4L, 4, 344)
            )
        );

        final List<ProblemInfoResponse> problems = Arrays.asList(
            new ProblemInfoResponse(1000L, 1L, (short) 1, (short) 1, "A+B", 126343L, 2.2986),
            new ProblemInfoResponse(1000L, 1L, (short) 1, (short) 1, "A+B", 126343L, 2.2986)
        );
        final ProblemResultResponse result = new ProblemResultResponse(1L, 1L, problems);
        final ProblemsResponse problemsResponse = new ProblemsResponse(true, result);
        final String expectedResult = mapper.writeValueAsString(problemsResponse);

        this.mockServer.expect(requestTo(SERVER_URL + SOLVED_PROBLEMS_URL + "test"))
            .andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        ProblemInfoResponses actual = userService.showSolvedProblemsOfUsers(userInfoResponses);
        assertThat(actual.getProblemInfoResponses()).hasSize(1);
        assertThat(actual.getProblemInfoResponses().get(0).getId()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("유저가 푼 중복이 없는 문제 리스트를 조회한다.")
    void showSolvedProblemsOfUsersNotDuplicate() throws JsonProcessingException {
        final UserInfoResponses userInfoResponses = new UserInfoResponses(
            Collections.singletonList(
                new UserInfoResponse("test", "", "test.test", 100L,
                    2000L, 3, 6, 4L, 0L, 4L, 4, 344)
            )
        );

        final List<ProblemInfoResponse> problems = Arrays.asList(
            new ProblemInfoResponse(1000L, 1L, (short) 1, (short) 1, "A+B", 126343L, 2.2986),
            new ProblemInfoResponse(1002L, 1L, (short) 1, (short) 1, "A+B+C", 126343L, 2.2986)
        );
        final ProblemResultResponse result = new ProblemResultResponse(1L, 1L, problems);
        final ProblemsResponse problemsResponse = new ProblemsResponse(true, result);
        final String expectedResult = mapper.writeValueAsString(problemsResponse);

        this.mockServer.expect(requestTo(SERVER_URL + SOLVED_PROBLEMS_URL + "test"))
            .andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        ProblemInfoResponses actual = userService.showSolvedProblemsOfUsers(userInfoResponses);
        assertThat(actual.getProblemInfoResponses()).hasSize(2);
        assertThat(actual.getProblemInfoResponses().get(0).getId()).isEqualTo(1000L);
        assertThat(actual.getProblemInfoResponses().get(1).getId()).isEqualTo(1002L);
    }
}
package com.konkuk.solvedac.api.application;

import static com.konkuk.solvedac.api.Constants.PER_PAGE_URL;
import static com.konkuk.solvedac.api.Constants.SERVER_URL;
import static com.konkuk.solvedac.api.Constants.USERS_GROUP_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konkuk.solvedac.user.dto.UserInfoResponse;
import com.konkuk.solvedac.user.dto.UserInfoResponses;
import com.konkuk.solvedac.user.dto.UserResultResponse;
import com.konkuk.solvedac.user.dto.UsersResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(UserInfoProvider.class)
class UserInfoProviderTest {

    @Autowired
    private UserInfoProvider userInfoProvider;

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("특정 그룹에 있는 유저를 가져온다.")
    void getUserInfoInGroup() throws JsonProcessingException {
        final Long expectedGroupId = 194L;
        final List<UserInfoResponse> users = Collections.singletonList(
            new UserInfoResponse("test", "", "test.test", 100L,
                2000L, 3, 6, 4L, 0L, 4L, 4, 344)
        );
        final UserResultResponse result = new UserResultResponse(1L, 1L, users);
        final UsersResponse usersResponse = new UsersResponse(true, result);
        final String expectedResult = mapper.writeValueAsString(usersResponse);

        this.mockServer.expect(requestTo(SERVER_URL + USERS_GROUP_URL + expectedGroupId + PER_PAGE_URL + "1"))
            .andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        final UserInfoResponses actual = userInfoProvider.getUserInfosInGroup(expectedGroupId);
        assertThat(actual.getUserInfoResponses()).hasSize(1);
        assertThat(actual.getUserInfoResponses().get(0).getUserId()).isEqualTo("test");
    }

    @Test
    @DisplayName("특정 그룹에 있는 유저들을 가져온다.")
    void getUserInfosInGroup() throws JsonProcessingException {
        final Long expectedGroupId = 194L;
        final List<UserInfoResponse> users = Arrays.asList(
            new UserInfoResponse("test", "", "test.test", 100L,
                2000L, 3, 6, 4L, 0L, 4L, 4, 344),
            new UserInfoResponse("test2", "", "test2.test2", 102L,
                2000L, 3, 6, 4L, 0L, 4L, 4, 344)
        );
        final UserResultResponse result = new UserResultResponse(1L, 1L, users);
        final UsersResponse usersResponse = new UsersResponse(true, result);
        final String expectedResult = mapper.writeValueAsString(usersResponse);

        this.mockServer.expect(requestTo(SERVER_URL + USERS_GROUP_URL + expectedGroupId + PER_PAGE_URL + "1"))
            .andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        final UserInfoResponses actual = userInfoProvider.getUserInfosInGroup(expectedGroupId);
        assertThat(actual.getUserInfoResponses()).hasSize(2);
        assertThat(actual.getUserInfoResponses().get(0).getUserId()).isEqualTo("test");
    }
}
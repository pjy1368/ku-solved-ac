package com.konkuk.solvedac.user;

import static com.konkuk.solvedac.user.UserFixture.GROUP_ID;
import static com.konkuk.solvedac.user.UserFixture.PLAYER_1;
import static org.assertj.core.api.Assertions.assertThat;

import com.konkuk.solvedac.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class UserAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("특정 그룹에 속한 유저 리스트를 조회한다.")
    void showUserInfosInGroup() {
        ExtractableResponse<Response> response = 특정_그룹_유저_조회_요청(GROUP_ID);
        특정_그룹_유저_목록_응답됨(response);
    }

    @Test
    @DisplayName("특정 유저가 푼 문제를 조회한다.")
    void showSolvedProblems() {
        ExtractableResponse<Response> response = 문제_조회_요청(PLAYER_1);
        문제_목록_응답됨(response);
    }

    @Test
    @DisplayName("존재하지 않은 유저거나 해당 유저가 푼 문제가 없을 경우 404번 에러가 발생한다.")
    void showSolvedProblemsNotFound() {
        ExtractableResponse<Response> response = 문제_조회_요청("12");
        문제_목록_실패됨_Not_Found(response);
    }

    @ParameterizedTest
    @DisplayName("유저의 아이디는 공백을 제외한 1글자 이상이어야 한다.")
    @ValueSource(strings = {"", " "})
    void showSolvedProblemsFail(String userId) {
        ExtractableResponse<Response> response = 문제_조회_요청(userId);
        문제_목록_실패됨(response);
    }

    @ParameterizedTest
    @DisplayName("특정 유저가 푼 문제를 티어 별로 조회한다.")
    @ValueSource(strings = {"unrated", "b5", "b4", "b3", "b2", "b1", "s5", "s4", "s3", "s2", "s1",
        "g5", "g4", "g3", "g2", "g1", "p5", "p4", "p3", "p2", "p1", "d5", "d4", "d3", "d2", "d1",
        "r5", "r4", "r3", "r2", "r1"})
    void showSolvedProblemsOfUsersByTier(String tier) {
        ExtractableResponse<Response> response = 특정_유저_맞은_문제_티어_별_조회_요청(PLAYER_1, tier);
        특정_유저_맞은_문제_티어_별_목록_응답됨(response);
    }

    public static ExtractableResponse<Response> 특정_그룹_유저_조회_요청(Long groupId) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/users?group_id=" + groupId)
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 문제_조회_요청(String userId) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/users/" + userId+ "/problems")
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 특정_유저_맞은_문제_티어_별_조회_요청(String userId, String tier) {
        return RestAssured
            .given().log().all()
            .when().get("/users/" + userId + "/problems/" + tier)
            .then().log().all()
            .extract();
    }

    private void 특정_그룹_유저_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 문제_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 문제_목록_실패됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private void 문제_목록_실패됨_Not_Found(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private void 특정_유저_맞은_문제_티어_별_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}

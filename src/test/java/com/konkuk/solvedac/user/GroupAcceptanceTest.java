package com.konkuk.solvedac.user;

import static com.konkuk.solvedac.user.UserAcceptanceTest.특정_그룹_유저_조회_요청;
import static com.konkuk.solvedac.user.UserFixture.GROUP_ID;
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

public class GroupAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("존재하지 않는 그룹이거나 그룹의 유저가 없으면 404번 에러가 발생한다.")
    void showUserInfosInGroupFail() {
        ExtractableResponse<Response> response = 특정_그룹_유저_조회_요청(-1L);
        특정_그룹_유저_목록_실패됨(response);
    }

    @Test
    @DisplayName("특정 그룹에서 푼 문제를 조회한다.")
    void showSolvedProblemsOfUsers() {
        ExtractableResponse<Response> response = 특정_그룹_맞은_문제_조회_요청(GROUP_ID);
        특정_그룹_맞은_문제_목록_응답됨(response);
    }

    @ParameterizedTest
    @DisplayName("특정 그룹에서 푼 문제를 티어 별로 조회한다.")
    @ValueSource(strings = {"unrated", "b5", "b4", "b3", "b2", "b1", "s5", "s4", "s3", "s2", "s1",
        "g5", "g4", "g3", "g2", "g1", "p5", "p4", "p3", "p2", "p1", "d5", "d4", "d3", "d2", "d1",
        "r5", "r4", "r3", "r2", "r1"})
    void showSolvedProblemsOfUsersByTier(String tier) {
        ExtractableResponse<Response> response = 특정_그룹_맞은_문제_티어_별_조회_요청(GROUP_ID, tier);
        특정_그룹_맞은_문제_티어_별_목록_응답됨(response);
    }

    @Test
    @DisplayName("특정 그룹에서 못 푼 문제를 조회한다.")
    void showUnsolvedProblemsOfUsers() {
        ExtractableResponse<Response> response = 특정_그룹_틀린_문제_조회_요청(GROUP_ID);
        특정_그룹_틀린_문제_목록_응답됨(response);
    }

    @ParameterizedTest
    @DisplayName("특정 그룹에서 못 푼 문제를 티어 별로 조회한다.")
    @ValueSource(strings = {"unrated", "b5", "b4", "b3", "b2", "b1", "s5", "s4", "s3", "s2", "s1",
        "g5", "g4", "g3", "g2", "g1", "p5", "p4", "p3", "p2", "p1", "d5", "d4", "d3", "d2", "d1",
        "r5", "r4", "r3", "r2", "r1"})
    void showUnsolvedProblemsOfUsersByTier(String tier) {
        ExtractableResponse<Response> response = 특정_그룹_틀린_문제_티어_별_조회_요청(GROUP_ID, tier);
        특정_그룹_틀린_문제_티어_별_목록_응답됨(response);
    }

    private ExtractableResponse<Response> 특정_그룹_맞은_문제_조회_요청(Long groupId) {
        return RestAssured
            .given().log().all()
            .when().get("/groups/" + groupId + "/solved-problems")
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 특정_그룹_맞은_문제_티어_별_조회_요청(Long groupId, String tier) {
        return RestAssured
            .given().log().all()
            .when().get("/groups/" + groupId + "/solved-problems/" + tier)
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 특정_그룹_틀린_문제_조회_요청(Long groupId) {
        return RestAssured
            .given().log().all()
            .when().get("/groups/" + groupId + "/unsolved-problems")
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 특정_그룹_틀린_문제_티어_별_조회_요청(Long groupId, String tier) {
        return RestAssured
            .given().log().all()
            .when().get("/groups/" + groupId + "/unsolved-problems/" + tier)
            .then().log().all()
            .extract();
    }

    private void 특정_그룹_유저_목록_실패됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private void 특정_그룹_맞은_문제_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 특정_그룹_맞은_문제_티어_별_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 특정_그룹_틀린_문제_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 특정_그룹_틀린_문제_티어_별_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}

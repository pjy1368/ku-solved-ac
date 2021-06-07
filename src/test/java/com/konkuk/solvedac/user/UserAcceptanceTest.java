package com.konkuk.solvedac.user;

import static com.konkuk.solvedac.user.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.konkuk.solvedac.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    @DisplayName("특정 그룹에서 못 푼 문제를 조회한다.")
    void showUnsolvedProblemsOfUsers() {
        ExtractableResponse<Response> response = 특정_그룹_틀린_문제_조회_요청(GROUP_ID);
        특정_그룹_틀린_문제_목록_응답됨(response);
    }

    private ExtractableResponse<Response> 특정_그룹_유저_조회_요청(Long groupId) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/users?groupId=" + groupId)
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 특정_그룹_맞은_문제_조회_요청(Long groupId) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(groupId)
            .when().post("/users/solved-problems")
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 특정_그룹_틀린_문제_조회_요청(Long groupId) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(groupId)
            .when().post("/users/unsolved-problems")
            .then().log().all()
            .extract();
    }

    private void 특정_그룹_유저_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 특정_그룹_유저_목록_실패됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private void 특정_그룹_맞은_문제_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 특정_그룹_틀린_문제_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}

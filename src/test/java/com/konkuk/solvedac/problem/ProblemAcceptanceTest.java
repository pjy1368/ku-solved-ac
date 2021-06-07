package com.konkuk.solvedac.problem;

import static com.konkuk.solvedac.user.UserFixture.PLAYER_1;
import static org.assertj.core.api.Assertions.assertThat;

import com.konkuk.solvedac.AcceptanceTest;
import com.konkuk.solvedac.problem.dto.UserId;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ProblemAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("전체 문제를 조회한다.")
    void showAllProblems() {
        ExtractableResponse<Response> response = 전체_문제_조회_요청();
        전체_문제_목록_응답됨(response);
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
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void showSolvedProblemsFail(String name) {
        ExtractableResponse<Response> response = 문제_조회_요청(name);
        문제_목록_실패됨(response);
    }

    private ExtractableResponse<Response> 전체_문제_조회_요청() {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/problems")
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 문제_조회_요청(String userId) {
        return RestAssured
            .given().log().all()
            .body(new UserId(userId))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/problems")
            .then().log().all()
            .extract();
    }

    private void 전체_문제_목록_응답됨(ExtractableResponse<Response> response) {
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
}

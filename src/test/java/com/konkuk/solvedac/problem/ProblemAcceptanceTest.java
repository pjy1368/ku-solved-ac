package com.konkuk.solvedac.problem;

import static org.assertj.core.api.Assertions.assertThat;

import com.konkuk.solvedac.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

public class ProblemAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("전체 문제를 조회한다.")
    void showAllProblems() {
        ExtractableResponse<Response> response = 전체_문제_조회_요청();
        전체_문제_목록_응답됨(response);
    }

    @ParameterizedTest
    @DisplayName("티어별 문제를 조회한다.")
    @ValueSource(strings = {"unrated", "b5", "b4", "b3", "b2", "b1", "s5", "s4", "s3", "s2", "s1",
        "g5", "g4", "g3", "g2", "g1", "p5", "p4", "p3", "p2", "p1", "d5", "d4", "d3", "d2", "d1",
        "r5", "r4", "r3", "r2", "r1"})
    void showProblemByTier(String tier) {
        ExtractableResponse<Response> response = 티어_별_문제_조회_요청(tier);
        티어_별_문제_목록_응답됨(response);
    }

    private ExtractableResponse<Response> 전체_문제_조회_요청() {
        return RestAssured
            .given().log().all()
            .when().get("/problems")
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 티어_별_문제_조회_요청(String tier) {
        return RestAssured
            .given().log().all()
            .when().get("/problems/" + tier)
            .then().log().all()
            .extract();
    }


    private void 전체_문제_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 티어_별_문제_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}

package com.konkuk.solvedac.problem;

import static org.assertj.core.api.Assertions.assertThat;

import com.konkuk.solvedac.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ProblemAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("전체 문제를 조회한다.")
    void showAllProblems() {
        ExtractableResponse<Response> response = 전체_문제_조회_요청();
        전체_문제_목록_응답됨(response);
    }

    private ExtractableResponse<Response> 전체_문제_조회_요청() {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/problems")
            .then().log().all()
            .extract();
    }


    private void 전체_문제_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}

package com.konkuk.solvedac.user.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.konkuk.solvedac.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LevelMapperTest {

    @ParameterizedTest
    @DisplayName("티어에 따른 레벨을 반환한다.")
    @ValueSource(strings = {"unrated", "b5", "b4", "b3", "b2", "b1", "s5", "s4", "s3", "s2", "s1",
        "g5", "g4", "g3", "g2", "g1", "p5", "p4", "p3", "p2", "p1", "d5", "d4", "d3", "d2", "d1",
        "r5", "r4", "r3", "r2", "r1", "00"})
    void tierToLevel(String tier) {
        assertThatCode(() -> LevelMapper.getLevel(tier)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("존재하지 않는 티어를 입력하면 에러가 발생한다.")
    void tierToLevelFail() {
        assertThatThrownBy(() -> LevelMapper.getLevel("test"))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("해당하는 티어가 존재하지 않습니다.");
    }
}
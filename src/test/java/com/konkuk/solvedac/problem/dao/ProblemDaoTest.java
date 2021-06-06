package com.konkuk.solvedac.problem.dao;

import static com.konkuk.solvedac.problem.ProblemFixture.PROBLEMS;
import static com.konkuk.solvedac.problem.ProblemFixture.SOLVED_PROBLEMS_BY_USER_1;
import static com.konkuk.solvedac.problem.ProblemFixture.SOLVED_PROBLEMS_BY_USER_2;
import static com.konkuk.solvedac.user.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.konkuk.solvedac.problem.domain.Problem;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;


@JdbcTest
@Sql("classpath:schema.sql")
class ProblemDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProblemDao problemDao;

    @BeforeEach
    void setUp() {
        problemDao = new ProblemDao(jdbcTemplate);
        problemDao.batchInsert(PROBLEMS);

        problemDao.batchInsert(PLAYER_1, GROUP_ID, SOLVED_PROBLEMS_BY_USER_1);
        problemDao.batchInsert(PLAYER_2, GROUP_ID, SOLVED_PROBLEMS_BY_USER_2);
    }

    @Test
    @DisplayName("모든 문제를 조회한다.")
    void findAllProblems() {
        assertThat(problemDao.findAllProblems()).isEqualTo(PROBLEMS);
    }

    @Test
    @DisplayName("특정 유저가 푼 문제를 조회한다.")
    void findByUserId() {
        assertThat(problemDao.findByUserId(PLAYER_1)).isEqualTo(SOLVED_PROBLEMS_BY_USER_1);
        assertThat(problemDao.findByUserId(PLAYER_2)).isEqualTo(SOLVED_PROBLEMS_BY_USER_2);
    }

    @Test
    @DisplayName("특정 그룹에서 푼 문제를 조회한다.")
    void findSolvedProblemByGroupId() {
        final Set<Problem> expected = new LinkedHashSet<>();
        expected.addAll(SOLVED_PROBLEMS_BY_USER_1);
        expected.addAll(SOLVED_PROBLEMS_BY_USER_2);
        assertThat(problemDao.findSolvedProblemByGroupId(GROUP_ID)).isEqualTo(new ArrayList<>(expected));
    }

    @Test
    @DisplayName("특정 그룹에서 못 푼 문제를 조회한다.")
    void findUnsolvedProblemByGroupId() {
        final Set<Problem> expected = new LinkedHashSet<>(PROBLEMS);
        expected.removeAll(SOLVED_PROBLEMS_BY_USER_1);
        expected.removeAll(SOLVED_PROBLEMS_BY_USER_2);
        assertThat(problemDao.findUnsolvedProblemByGroupId(GROUP_ID)).isEqualTo(new ArrayList<>(expected));
    }

    @Test
    @DisplayName("모든 문제 리스트를 삭제한다.")
    void deleteAllProblems() {
        problemDao.deleteAllProblems();
        assertThat(problemDao.findAllProblems()).hasSize(0);
    }

    @Test
    @DisplayName("모든 유저가 푼 문제 리스트 정보를 삭제한다.")
    void deleteAllProblemMap() {
        assertThatCode(() -> problemDao.deleteAllProblemMap())
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("특정 유저가 푼 문제 리스트가 있는지 확인한다.")
    void isAlreadyMappedUserProblems() {
        assertThat(problemDao.isAlreadyMappedUserProblems(PLAYER_1)).isTrue();
        assertThat(problemDao.isAlreadyMappedUserProblems(PLAYER_2)).isTrue();
        assertThat(problemDao.isAlreadyMappedUserProblems("xxx")).isFalse();
    }
}
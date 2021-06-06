package com.konkuk.solvedac.problem.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.konkuk.solvedac.problem.domain.Problem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
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

    private final List<Problem> problems = Arrays.asList(
        new Problem(1L, "one"),
        new Problem(2L, "two"),
        new Problem(3L, "three"),
        new Problem(4L, "four"),
        new Problem(5L, "five"),
        new Problem(6L, "six"),
        new Problem(7L, "seven"),
        new Problem(8L, "eight"),
        new Problem(9L, "nine"),
        new Problem(10L, "ten")
    );

    private final List<Problem> solvedProblemsByUser1 = Arrays.asList(
        new Problem(1L, "one"),
        new Problem(2L, "two"),
        new Problem(3L, "three")
    );

    private final List<Problem> solvedProblemsByUser2 = Arrays.asList(
        new Problem(7L, "seven"),
        new Problem(8L, "eight"),
        new Problem(9L, "nine"),
        new Problem(10L, "ten")
    );

    @BeforeEach
    void setUp() {
        problemDao = new ProblemDao(jdbcTemplate);
        problemDao.batchInsert(problems);

        final Long groupId = 194L;
        problemDao.batchInsert("pjy1368", groupId, solvedProblemsByUser1);
        problemDao.batchInsert("whitePiano", groupId, solvedProblemsByUser2);
    }

    @Test
    @DisplayName("모든 문제를 조회한다.")
    void findAllProblems() {
        assertThat(problemDao.findAllProblems()).isEqualTo(problems);
    }

    @Test
    @DisplayName("특정 유저가 푼 문제를 조회한다.")
    void findByUserId() {
        assertThat(problemDao.findByUserId("pjy1368")).isEqualTo(solvedProblemsByUser1);
        assertThat(problemDao.findByUserId("whitePiano")).isEqualTo(solvedProblemsByUser2);
    }

    @Test
    @DisplayName("특정 그룹에서 푼 문제를 조회한다.")
    void findSolvedProblemByGroupId() {
        final Set<Problem> expected = new LinkedHashSet<>();
        expected.addAll(solvedProblemsByUser1);
        expected.addAll(solvedProblemsByUser2);
        assertThat(problemDao.findSolvedProblemByGroupId(194L)).isEqualTo(new ArrayList<>(expected));
    }

    @Test
    @DisplayName("특정 그룹에서 못 푼 문제를 조회한다.")
    void findUnsolvedProblemByGroupId() {
        final Set<Problem> expected = new LinkedHashSet<>(problems);
        expected.removeAll(solvedProblemsByUser1);
        expected.removeAll(solvedProblemsByUser2);
        assertThat(problemDao.findUnsolvedProblemByGroupId(194L)).isEqualTo(new ArrayList<>(expected));
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
}
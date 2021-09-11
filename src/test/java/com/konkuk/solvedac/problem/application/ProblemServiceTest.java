package com.konkuk.solvedac.problem.application;

import static com.konkuk.solvedac.problem.ProblemFixture.PROBLEMS;
import static com.konkuk.solvedac.problem.ProblemFixture.SOLVED_PROBLEMS_BY_USER_1;
import static com.konkuk.solvedac.problem.ProblemFixture.SOLVED_PROBLEMS_BY_USER_2;
import static com.konkuk.solvedac.user.UserFixture.GROUP_ID;
import static com.konkuk.solvedac.user.UserFixture.PLAYER_1;
import static com.konkuk.solvedac.user.UserFixture.PLAYER_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.konkuk.solvedac.problem.dao.ProblemDao;
import com.konkuk.solvedac.problem.domain.Problem;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponse;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.user.domain.LevelMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProblemServiceTest {

    @InjectMocks
    private ProblemService problemService;

    @Mock
    private ProblemDao problemDao;

    @Test
    @DisplayName("모든 문제를 조회한다.")
    void findAllProblems() {
        given(problemDao.findAllProblems()).willReturn(PROBLEMS);
        final List<Problem> actual = dtoToEntity(problemService.findAllProblems());

        assertThat(actual).isEqualTo(PROBLEMS);
        verify(problemDao, times(1)).findAllProblems();
    }

    @ParameterizedTest
    @DisplayName("티어별 문제를 조회한다.")
    @ValueSource(strings = {"unrated", "b5", "b4", "b3", "b2", "b1", "s5", "s4", "s3", "s2", "s1",
        "g5", "g4", "g3", "g2", "g1", "p5", "p4", "p3", "p2", "p1", "d5", "d4", "d3", "d2", "d1",
        "r5", "r4", "r3", "r2", "r1"})
    void findProblemByTier(String tier) {
        final int level = LevelMapper.getLevel(tier);
        final List<Problem> expected = Collections.singletonList(
            new Problem(1, level, "test", 10)
        );
        given(problemDao.findProblemByLevel(level)).willReturn(expected);

        final List<Problem> actual = dtoToEntity(problemService.findProblemByTier(tier));
        assertThat(actual).isEqualTo(expected);
        verify(problemDao, times(1)).findProblemByLevel(level);
    }

    @Test
    @DisplayName("특정 유저가 푼 문제를 조회한다.")
    void findByUserId() {
        given(problemDao.findByUserId(PLAYER_1)).willReturn(SOLVED_PROBLEMS_BY_USER_1);
        final List<Problem> actual = dtoToEntity(problemService.findSolvedProblemByUserId(PLAYER_1));

        assertThat(actual).isEqualTo(SOLVED_PROBLEMS_BY_USER_1);
        verify(problemDao, times(1)).findByUserId(PLAYER_1);
    }

    @Test
    @DisplayName("특정 그룹에서 푼 문제를 조회한다.")
    void findSolvedProblemByGroupId() {
        final Set<Problem> expected = new LinkedHashSet<>();
        expected.addAll(SOLVED_PROBLEMS_BY_USER_1);
        expected.addAll(SOLVED_PROBLEMS_BY_USER_2);
        given(problemDao.findSolvedProblemByGroupId(GROUP_ID)).willReturn(new ArrayList<>(expected));

        final List<Problem> actual = dtoToEntity(problemService.findSolvedProblemByGroupId(GROUP_ID));
        assertThat(actual).isEqualTo(new ArrayList<>(expected));
        verify(problemDao, times(1)).findSolvedProblemByGroupId(GROUP_ID);
    }

    @Test
    @DisplayName("특정 그룹에서 못 푼 문제를 조회한다.")
    void findUnsolvedProblemByGroupId() {
        final Set<Problem> expected = new LinkedHashSet<>(PROBLEMS);
        expected.removeAll(SOLVED_PROBLEMS_BY_USER_1);
        expected.removeAll(SOLVED_PROBLEMS_BY_USER_2);
        given(problemDao.findUnsolvedProblemByGroupId(GROUP_ID)).willReturn(new ArrayList<>(expected));

        final List<Problem> actual = dtoToEntity(problemService.findUnsolvedProblemByGroupId(GROUP_ID));
        assertThat(actual).isEqualTo(new ArrayList<>(expected));
        verify(problemDao, times(1)).findUnsolvedProblemByGroupId(GROUP_ID);
    }

    @Test
    @DisplayName("특정 유저가 푼 문제 리스트가 있는지 확인한다.")
    void isAlreadyMappedUserProblems() {
        given(problemDao.isAlreadyMappedUserProblems(PLAYER_1)).willReturn(true);
        given(problemDao.isAlreadyMappedUserProblems(PLAYER_2)).willReturn(true);
        given(problemDao.isAlreadyMappedUserProblems("xxx")).willReturn(false);

        assertThat(problemService.isAlreadyMappedUserProblems(PLAYER_1)).isTrue();
        assertThat(problemService.isAlreadyMappedUserProblems(PLAYER_2)).isTrue();
        assertThat(problemService.isAlreadyMappedUserProblems("xxx")).isFalse();

        verify(problemDao, times(1)).isAlreadyMappedUserProblems(PLAYER_1);
        verify(problemDao, times(1)).isAlreadyMappedUserProblems(PLAYER_2);
        verify(problemDao, times(1)).isAlreadyMappedUserProblems("xxx");
    }

    private List<Problem> dtoToEntity(ProblemInfoResponses dto) {
        return dto.getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity)
            .collect(Collectors.toList());
    }
}
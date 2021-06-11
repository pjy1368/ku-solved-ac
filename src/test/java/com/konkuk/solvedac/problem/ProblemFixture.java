package com.konkuk.solvedac.problem;

import com.konkuk.solvedac.problem.domain.Problem;
import java.util.Arrays;
import java.util.List;

public class ProblemFixture {

    public static final int LEVEL_1 = 1;

    public static final List<Problem> PROBLEMS = Arrays.asList(
        new Problem(1L, 1, "one", 1L),
        new Problem(2L, 2, "two", 2L),
        new Problem(3L, 3, "three", 3L),
        new Problem(4L, 4, "four", 4L),
        new Problem(5L, 5, "five", 5L),
        new Problem(6L, 6, "six", 6L),
        new Problem(7L, 7, "seven", 7L),
        new Problem(8L, 8, "eight", 8L),
        new Problem(9L, 9, "nine", 9L),
        new Problem(10L, 10, "ten", 10L)
    );

    public static final List<Problem> SOLVED_PROBLEMS_BY_USER_1 = Arrays.asList(
        new Problem(1L, 1, "one", 1L),
        new Problem(2L, 2, "two", 2L),
        new Problem(3L, 3, "three", 3L)
    );

    public static final List<Problem> SOLVED_PROBLEMS_BY_USER_2 = Arrays.asList(
        new Problem(7L, 7, "seven", 7L),
        new Problem(8L, 8, "eight", 8L),
        new Problem(9L, 9, "nine", 9L),
        new Problem(10L, 10, "ten", 10L)
    );

    public static final List<Problem> SOLVED_PROBLEMS_BY_USER_3 = Arrays.asList(
        new Problem(7L, 7, "seven", 7L),
        new Problem(8L, 8, "eight", 8L),
        new Problem(9L, 9, "nine", 9L),
        new Problem(10L, 10, "ten", 10L)
    );
}

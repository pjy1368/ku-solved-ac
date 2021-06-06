package com.konkuk.solvedac.problem;

import com.konkuk.solvedac.problem.domain.Problem;
import java.util.Arrays;
import java.util.List;

public class ProblemFixture {
    public static final List<Problem> PROBLEMS = Arrays.asList(
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

    public static final List<Problem> SOLVED_PROBLEMS_BY_USER_1 = Arrays.asList(
        new Problem(1L, "one"),
        new Problem(2L, "two"),
        new Problem(3L, "three")
    );

    public static final List<Problem> SOLVED_PROBLEMS_BY_USER_2 = Arrays.asList(
        new Problem(7L, "seven"),
        new Problem(8L, "eight"),
        new Problem(9L, "nine"),
        new Problem(10L, "ten")
    );
}

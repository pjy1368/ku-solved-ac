package com.konkuk.solvedac.problem;

import com.konkuk.solvedac.problem.domain.Problem;
import java.util.Arrays;
import java.util.List;

public class ProblemFixture {

    public static final List<Problem> PROBLEMS = Arrays.asList(
        new Problem(1L, 1, "one" ),
        new Problem(2L, 2, "two" ),
        new Problem(3L, 3, "three" ),
        new Problem(4L, 4, "four" ),
        new Problem(5L, 5, "five" ),
        new Problem(6L, 6, "six"),
        new Problem(7L, 7, "seven"),
        new Problem(8L, 8, "eight"),
        new Problem(9L, 9, "nine"),
        new Problem(10L, 10, "ten")
    );

    public static final List<Problem> SOLVED_PROBLEMS_BY_USER_1 = Arrays.asList(
        new Problem(1L, 1, "one"),
        new Problem(2L, 2, "two"),
        new Problem(3L, 3, "three")
    );

    public static final List<Problem> SOLVED_PROBLEMS_BY_USER_2 = Arrays.asList(
        new Problem(7L, 7, "seven"),
        new Problem(8L, 8, "eight"),
        new Problem(9L, 9, "nine"),
        new Problem(10L, 10, "ten")
    );

    public static final List<Problem> SOLVED_PROBLEMS_BY_USER_3 = Arrays.asList(
        new Problem(7L, 7, "seven"),
        new Problem(8L, 8, "eight"),
        new Problem(9L, 9, "nine"),
        new Problem(10L, 10, "ten")
    );
}

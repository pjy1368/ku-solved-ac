package com.konkuk.solvedac.problem;

import com.konkuk.solvedac.problem.domain.Problem;
import java.util.Arrays;
import java.util.List;

public class ProblemFixture {

    public static final int LEVEL_1 = 1;

    public static final List<Problem> PROBLEMS = Arrays.asList(
        new Problem(1, 1, "one", 1),
        new Problem(2, 2, "two", 2),
        new Problem(3, 3, "three", 3),
        new Problem(4, 4, "four", 4),
        new Problem(5, 5, "five", 5),
        new Problem(6, 6, "six", 6),
        new Problem(7, 7, "seven", 7),
        new Problem(8, 8, "eight", 8),
        new Problem(9, 9, "nine", 9),
        new Problem(10, 10, "ten", 10)
    );

    public static final List<Problem> SOLVED_PROBLEMS_BY_USER_1 = Arrays.asList(
        new Problem(1, 1, "one", 1),
        new Problem(2, 2, "two", 2),
        new Problem(3, 3, "three", 3)
    );

    public static final List<Problem> SOLVED_PROBLEMS_BY_USER_2 = Arrays.asList(
        new Problem(7, 7, "seven", 7),
        new Problem(8, 8, "eight", 8),
        new Problem(9, 9, "nine", 9),
        new Problem(10, 10, "ten", 10)
    );

    public static final List<Problem> SOLVED_PROBLEMS_BY_USER_3 = Arrays.asList(
        new Problem(7, 7, "seven", 7),
        new Problem(8, 8, "eight", 8),
        new Problem(9, 9, "nine", 9),
        new Problem(10, 10, "ten", 10)
    );
}

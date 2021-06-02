package com.konkuk.solvedac.problem.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Problem {

    private final Long id;
    private final String title;
}

package com.konkuk.solvedac.problem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Problem {

    private Integer id;

    private Integer level;

    private String title;

    private Integer solvedCount;
}

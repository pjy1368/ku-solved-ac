package com.konkuk.solvedac.problem.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Problem {

    private Long id;
    @NonNull
    private Long problemId;
    @NonNull
    private String title;
}

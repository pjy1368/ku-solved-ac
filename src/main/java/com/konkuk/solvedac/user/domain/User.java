package com.konkuk.solvedac.user.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class User {

    private String id;
    private Long groupId;
}

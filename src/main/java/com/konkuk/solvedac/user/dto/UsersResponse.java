package com.konkuk.solvedac.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UsersResponse {

    private boolean success;
    private UserResultResponse result;
}

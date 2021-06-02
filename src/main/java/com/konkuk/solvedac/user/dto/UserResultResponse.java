package com.konkuk.solvedac.user.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResultResponse {

    private Long totalUser;
    private Long totalPage;
    private List<UserInfoResponse> users;
}

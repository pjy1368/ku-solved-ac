package com.konkuk.solvedac.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer totalPages;

    @JsonProperty("count")
    private Integer totalUsers;

    @JsonProperty("items")
    private List<UserInfoResponse> userInfoResponses;
}

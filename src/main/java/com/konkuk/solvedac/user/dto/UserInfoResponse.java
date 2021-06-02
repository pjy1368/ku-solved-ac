package com.konkuk.solvedac.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserInfoResponse {

    private String userId;
    private String bio;
    private String profileImageUrl;
    private Long solved;
    private Long exp;
    private Integer rating;
    private Integer level;
    @JsonProperty("class")
    private Long classLevel;
    private Long classDecoration;
    private Long voteCount;
    private Integer rank;
    private Integer globalRank;
}

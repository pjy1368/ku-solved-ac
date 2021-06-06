package com.konkuk.solvedac.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.konkuk.solvedac.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class UserInfoResponse {

    @NonNull
    @JsonProperty("user_id")
    private String id;
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

    public User toEntity(Long groupId) {
        return new User(id, groupId);
    }
}

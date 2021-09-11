package com.konkuk.solvedac.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.konkuk.solvedac.user.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {

    @JsonProperty("handle")
    private String id;

    private String bio;

    private List<Organization> organizations;

    private Badge badge;

    private Background background;

    private String profileImageUrl;

    private Integer solvedCount;

    private Integer voteCount;

    @JsonProperty("class")
    private Integer classLevel;

    private String classDecoration;

    private Integer tier;

    private Integer rating;

    private Integer ratingByProblemsSum;

    private Integer ratingByClass;

    private Integer ratingBySolvedCount;

    private Integer ratingByVoteCount;

    private Long exp;

    private Integer rivalCount;

    private Integer reverseRivalCount;

    private Integer maxStreak;

    private Integer rank;

    private Integer globalRank;

    public static UserInfoResponse from(User user) {
        return builder()
            .id(user.getId())
            .build();
    }

    public User toEntity(int groupId) {
        return User.builder()
            .id(id)
            .groupId(groupId)
            .build();
    }
}

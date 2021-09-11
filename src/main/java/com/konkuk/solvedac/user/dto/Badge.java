package com.konkuk.solvedac.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Badge {

    private String badgeId;

    private String badgeImageUrl;

    private String displayName;

    private String displayDescription;
}

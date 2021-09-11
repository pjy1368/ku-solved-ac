package com.konkuk.solvedac.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Background {

    private String backgroundId;

    private String backgroundImageUrl;

    private String author;

    private String displayName;

    private String displayDescription;
}

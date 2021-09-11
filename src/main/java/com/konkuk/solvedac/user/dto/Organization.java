package com.konkuk.solvedac.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    private String organizationId;

    private String name;

    private String type;

    private Integer rating;

    private Integer userCount;

    private Integer voteCount;

    private Integer solvedCount;

    private String color;
}

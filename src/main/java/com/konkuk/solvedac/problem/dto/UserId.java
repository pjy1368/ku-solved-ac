package com.konkuk.solvedac.problem.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserId {

    @NotBlank(message = "유저의 아이디는 1글자 이상이어야 합니다.")
    private String id;
}

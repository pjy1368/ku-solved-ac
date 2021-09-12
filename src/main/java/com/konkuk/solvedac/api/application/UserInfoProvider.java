package com.konkuk.solvedac.api.application;

import static com.konkuk.solvedac.api.Constants.DATA_COUNT_PER_PAGE;
import static com.konkuk.solvedac.api.Constants.PER_PAGE_URL;
import static com.konkuk.solvedac.api.Constants.SERVER_URL;
import static com.konkuk.solvedac.api.Constants.USERS_GROUP_URL;

import com.konkuk.solvedac.user.dto.UserInfoResponse;
import com.konkuk.solvedac.user.dto.UserInfoResponses;
import com.konkuk.solvedac.user.dto.UserResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserInfoProvider {

    private final RestTemplate template;

    public UserInfoProvider(RestTemplateBuilder restTemplateBuilder) {
        this.template = restTemplateBuilder.build();
    }

    public UserInfoResponses getUserInfosInGroup(Integer groupId) {
        final String url = SERVER_URL + USERS_GROUP_URL + groupId;
        return requestUserInfosInGroup(url);
    }

    private UserInfoResponses requestUserInfosInGroup(String url) {
        try {
            final UserResponse userResponse = template.getForObject(url, UserResponse.class);
            Thread.sleep(1200);

            final int totalPages = (int) Math.ceil((userResponse.getTotalUsers() / DATA_COUNT_PER_PAGE));
            List<UserInfoResponse> userInfoResponses = new ArrayList<>(userResponse.getUserInfoResponses());

            for (int i = 2; i <= totalPages; i++) {
                userInfoResponses.addAll(Objects.requireNonNull(template.getForObject(url + PER_PAGE_URL + i,
                    UserResponse.class).getUserInfoResponses()));
                Thread.sleep(1200);
            }
            return new UserInfoResponses(userInfoResponses);
        } catch (InterruptedException e) {
            throw new RuntimeException("API 요청 대기 시간 중 에러가 발생했습니다.");
        }
    }
}

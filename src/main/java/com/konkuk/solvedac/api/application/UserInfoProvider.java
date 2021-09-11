package com.konkuk.solvedac.api.application;

import static com.konkuk.solvedac.api.Constants.PER_PAGE_URL;
import static com.konkuk.solvedac.api.Constants.SERVER_URL_V3;
import static com.konkuk.solvedac.api.Constants.USERS_GROUP_URL_V3;

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

    public UserInfoResponses getUserInfosInGroup(Long groupId) {
        final String url = SERVER_URL_V3 + USERS_GROUP_URL_V3 + groupId;
        return requestUserInfosInGroup(url);
    }

    private UserInfoResponses requestUserInfosInGroup(String url) {
        final UserResponse userResponse = template.getForObject(url, UserResponse.class);
        userResponse.setTotalPages((int) Math.ceil((userResponse.getTotalUsers() / 100.0)));

        final int totalPages = userResponse.getTotalPages();
        List<UserInfoResponse> userInfoResponses = new ArrayList<>(userResponse.getUserInfoResponses());

        for (int i = 2; i <= totalPages; i++) {
            userInfoResponses.addAll(Objects.requireNonNull(template.getForObject(url + PER_PAGE_URL + i,
                UserResponse.class).getUserInfoResponses()));
        }
        return new UserInfoResponses(userInfoResponses);
    }
}

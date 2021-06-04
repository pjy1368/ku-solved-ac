package com.konkuk.solvedac.api.application;

import static com.konkuk.solvedac.api.Constants.PER_PAGE_URL;
import static com.konkuk.solvedac.api.Constants.SERVER_URL;
import static com.konkuk.solvedac.api.Constants.USERS_GROUP_URL;

import com.konkuk.solvedac.user.dto.UserInfoResponse;
import com.konkuk.solvedac.user.dto.UserInfoResponses;
import com.konkuk.solvedac.user.dto.UserResultResponse;
import com.konkuk.solvedac.user.dto.UsersResponse;
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
        final String url = SERVER_URL + USERS_GROUP_URL + groupId + PER_PAGE_URL + "1";
        return requestUserInfosInGroup(url);
    }

    private UserInfoResponses requestUserInfosInGroup(String url) {
        final UsersResponse initialUsersResponse = template.getForObject(url, UsersResponse.class);
        final UserResultResponse result = Objects.requireNonNull(initialUsersResponse).getResult();

        final long totalPages = Objects.requireNonNull(result.getTotalPage());
        final List<UserInfoResponse> userInfoResponses = new ArrayList<>(result.getUsers());

        for (int i = 2; i <= totalPages; i++) {
            userInfoResponses.addAll(Objects.requireNonNull(template.getForObject(url + PER_PAGE_URL + i,
                UsersResponse.class)).getResult().getUsers());
        }
        return new UserInfoResponses(userInfoResponses);
    }
}

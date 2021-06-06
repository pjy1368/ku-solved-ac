package com.konkuk.solvedac.user;

import com.konkuk.solvedac.user.domain.User;
import java.util.Arrays;
import java.util.List;

public class UserFixture {

    public static final String PLAYER_1 = "pjy1368";
    public static final String PLAYER_2 = "whitePiano";
    public static final Long GROUP_ID = 194L;

    public static final List<User> USERS = Arrays.asList(
        new User("player1", 194L),
        new User("player2", 194L)
    );
}

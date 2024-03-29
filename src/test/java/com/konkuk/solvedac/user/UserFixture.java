package com.konkuk.solvedac.user;

import com.konkuk.solvedac.user.domain.User;
import java.util.Arrays;
import java.util.List;

public class UserFixture {

    public static final String PLAYER_1 = "pjy1368";
    public static final String PLAYER_2 = "whitePiano";
    public static final String PLAYER_3 = "test";
    public static final Integer GROUP_ID = 194;

    public static final List<User> USERS = Arrays.asList(
        new User("abcd", 195),
        new User(PLAYER_1, 194),
        new User(PLAYER_2, 194),
        new User(PLAYER_3, null)
    );

    public static final List<User> KON_KUK_USERS = Arrays.asList(
        new User(PLAYER_1, 194),
        new User(PLAYER_2, 194)
    );
}

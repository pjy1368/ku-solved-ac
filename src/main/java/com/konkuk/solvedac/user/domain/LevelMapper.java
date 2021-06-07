package com.konkuk.solvedac.user.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelMapper {

    public static final Map<String, Integer> MAP = new HashMap<>();
    private static final List<String> tiers = Arrays.asList("b", "s", "g", "p", "d", "r");

    static {
        MAP.put("unrated", 0);
        int level = 1;
        for (final String tier : tiers) {
            for (int i = 5; i > 0; i--) {
                MAP.put(tier + i, level++);
            }
        }
    }
}

package com.konkuk.solvedac.user.domain;

import com.konkuk.solvedac.exception.NotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelMapper {

    private static final Map<String, Integer> MAP = new HashMap<>();
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

    public static int getLevel(String tier) {
        if (!MAP.containsKey(tier)) {
            throw new NotFoundException("해당하는 티어가 존재하지 않습니다.");
        }
        return MAP.get(tier);
    }
}

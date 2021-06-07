package com.konkuk.solvedac.user.domain;

import java.util.HashMap;
import java.util.Map;

public class LevelMapper {
    public static Map<String, Integer> map = new HashMap<>();

    static {
        map.put("unrated", 0);
        map.put("b5", 1);
        map.put("b4", 2);
        map.put("b3", 3);
        map.put("b2", 4);
        map.put("b1", 5);
        map.put("s5", 6);
        map.put("s4", 7);
        map.put("s3", 8);
        map.put("s2", 9);
        map.put("s1", 10);
        map.put("g5", 11);
        map.put("g4", 12);
        map.put("g3", 13);
        map.put("g2", 14);
        map.put("g1", 15);
        map.put("p5", 16);
        map.put("p4", 17);
        map.put("p3", 18);
        map.put("p2", 19);
        map.put("p1", 20);
        map.put("d5", 21);
        map.put("d4", 22);
        map.put("d3", 23);
        map.put("d2", 24);
        map.put("d1", 25);
        map.put("r5", 26);
        map.put("r4", 27);
        map.put("r3", 28);
        map.put("r2", 29);
        map.put("r1", 30);
    }
}

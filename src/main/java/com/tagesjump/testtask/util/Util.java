package com.tagesjump.testtask.util;

import com.tagesjump.testtask.model.User;

import java.util.Map;

public class Util {

    public static final User mockUser1 = new User(1, "someToken", "Mike", 35);
    public static final User mockUser2 = new User(2, "anyToken", "John", 25);

    public static void populateMap(Map<Integer, User> map) {
        map.clear();
        map.put(mockUser1.getId(), mockUser1);
        map.put(mockUser2.getId(), mockUser2);
    }
}

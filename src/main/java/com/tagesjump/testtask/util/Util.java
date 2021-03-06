package com.tagesjump.testtask.util;

import com.tagesjump.testtask.model.User;

import java.util.Map;

public class Util {
    public static void populateMap(Map<Integer, User> map) {
        map.clear();
        map.put(1, new User(1, "someToken", "Mike", 35));
        map.put(2, new User(2, "anyToken", "John", 25));
    }
}
package com.tagesjump.testtask;

import com.tagesjump.testtask.model.User;

public class UserTestData {
    public static final User mockUser1 = new User(1, "someToken", "Mike", 35);
    public static final User mockUser2 = new User(2, "anyToken", "John", 25);
    public static final int NOT_FOUND_ID = 10;
    public static final int NEW_ID = 3;

    public static User getNameAndAgeUpdated(User user) {
        return new User(
                user.getId(),
                user.getToken(),
                "updatedName",
                20
        );
    }

    public static User getAgeUpdated(User user) {
        return new User(
                user.getId(),
                user.getToken(),
                user.getName(),
                30
        );
    }

    public static User getFullUpdated() {
        return new User(
                NEW_ID,
                "newToken",
                "updatedName",
                20
        );
    }

    public static String getJsonToUpdateUserAge() {
        return "{\"age\":\"30\"}";
    }

    public static String getBadJson() {
        return "{\"name\":\"\", \"age\":\"-30\"}";
    }
}

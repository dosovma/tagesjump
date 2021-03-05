package com.tagesjump.testtask.model;

import com.tagesjump.testtask.web.exception.BasicInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Objects;

public class User {
    @NotNull
    private Integer id;

    @NotBlank(message = "Token can't be empty")
    private String token;

    @Size(min = 2, message = "Name min length is 2", groups = BasicInfo.class)
    private String name;

    @Positive(message = "Age must be positive", groups = BasicInfo.class)
    private Integer age;

    public User(Integer id, String token, String name, Integer age) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(token, user.token) && Objects.equals(name, user.name) && Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, name, age);
    }
}

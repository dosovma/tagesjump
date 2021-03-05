package com.tagesjump.testtask.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class User {
    @NotNull
    private Integer id;

    @NotBlank(message = "Token can't be empty")
    private String token;

    @NotBlank(message = "Name can't be empty")
    private String name;

    @Positive(message = "Age must be positive")
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
}

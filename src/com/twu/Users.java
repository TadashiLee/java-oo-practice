package com.twu;

public class Users {
    private String name;
    private Integer userPoll=10;

    public Users() {
    }

    public Users(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserPoll() {
        return userPoll;
    }

    public void setUserPoll(Integer userPoll) {
        this.userPoll = userPoll;
    }
}

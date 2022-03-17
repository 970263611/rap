package com.psbc.rpa.model;

/**
 * @author dahua
 * @time 2022/3/14 10:47
 */
public class RpaUser {

    private String userId;
    private String username;
    private String password;
    private Object session;

    public RpaUser() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getSession() {
        return session;
    }

    public void setSession(Object session) {
        this.session = session;
    }
}

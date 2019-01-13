package com.fenixbao92.shiro.dogcatlogin.domain;

/**
 * Create by Fenix_Bao on 2019/1/6.
 */
public class Session {

    private String id;
    private String session;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

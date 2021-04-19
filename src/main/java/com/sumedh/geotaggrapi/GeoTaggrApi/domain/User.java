package com.sumedh.geotaggrapi.GeoTaggrApi.domain;

public class User {
    private Integer userId;
    private String name;
    private String facebookId;
    private String fcmToken;

    public User(Integer userId, String name, String facebookId, String fcmToken) {
        this.userId = userId;
        this.name = name;
        this.facebookId = facebookId;
        this.fcmToken = fcmToken;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

}

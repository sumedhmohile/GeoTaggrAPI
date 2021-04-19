package com.sumedh.geotaggrapi.GeoTaggrApi.services;

import com.sumedh.geotaggrapi.GeoTaggrApi.domain.User;

public interface UserService {
    User loginUser(String name, String facebookId, String fcmToken);

    String generateUserJWT(User user);
}

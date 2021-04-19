package com.sumedh.geotaggrapi.GeoTaggrApi.repositories;

import com.sumedh.geotaggrapi.GeoTaggrApi.domain.User;

public interface UserRepository {
    Integer addUser(String name, String facebookId, String fcmToken);

    Integer getUserCountByFacebookId(String facebookId);

    User getUserById(Integer userId);

    User getUserByFacebookId(String facebookId);
}

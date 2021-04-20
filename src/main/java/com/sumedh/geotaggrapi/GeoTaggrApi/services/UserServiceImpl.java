package com.sumedh.geotaggrapi.GeoTaggrApi.services;

import com.sumedh.geotaggrapi.GeoTaggrApi.Constants;
import com.sumedh.geotaggrapi.GeoTaggrApi.domain.User;
import com.sumedh.geotaggrapi.GeoTaggrApi.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Value("${secretKey}")
    String secretKey;

    @Override
    public User loginUser(String name, String facebookId, String fcmToken) {
        User user;
        Integer userCount = userRepository.getUserCountByFacebookId(facebookId);

        if(userCount == 0) {
            userRepository.addUser(name, facebookId, fcmToken);
        }

        user = userRepository.getUserByFacebookId(facebookId);

        return user;
    }

    @Override
    public String generateUserJWT(User user) {
        long tokenCreationDate = System.currentTimeMillis();

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setIssuedAt(new Date(tokenCreationDate))
                .setExpiration(new Date(tokenCreationDate + Constants.TOKEN_EXPIRATION_TIME))
                .claim("userId", user.getUserId())
                .claim("name", user.getName())
                .claim("facebookId", user.getFacebookId())
                .claim("fcmToken", user.getFcmToken())
                .compact();

        return token;
    }
}

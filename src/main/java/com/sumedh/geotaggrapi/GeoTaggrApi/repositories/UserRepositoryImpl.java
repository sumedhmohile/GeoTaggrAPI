package com.sumedh.geotaggrapi.GeoTaggrApi.repositories;

import com.sumedh.geotaggrapi.GeoTaggrApi.domain.User;
import com.sumedh.geotaggrapi.GeoTaggrApi.exceptions.GTAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private static final String SQL_ADD_USER = "INSERT INTO GT_USERS(USER_ID, NAME, FB_ID, FCM_TOKEN) VALUES(NEXTVAL('GT_USER_SEQ'), ?, ?, ?)";
    private static final String SQL_GET_USER_BY_ID = "SELECT USER_ID, NAME, FB_ID, FCM_TOKEN FROM GT_USERS WHERE USER_ID=?";
    private static final String SQL_GET_USER_COUNT_BY_FB_ID = "SELECT COUNT(*) FROM GT_USERS WHERE FB_ID=?";
    private static final String SQL_GET_USER_BY_FB_ID = "SELECT USER_ID, NAME, FB_ID, FCM_TOKEN FROM GT_USERS WHERE FB_ID=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> new User(rs.getInt("USER_ID"), rs.getString("NAME"), rs.getString("FB_ID"), rs.getString("FCM_TOKEN")));

    @Override
    public Integer addUser(String name, String facebookId, String fcmToken) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_ADD_USER, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, name);
                ps.setString(2, facebookId);
                ps.setString(3, fcmToken);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("USER_ID");

        } catch (Exception e) {
            e.printStackTrace();
            throw new GTAuthException("Error when adding user");
        }
    }

    @Override
    public Integer getUserCountByFacebookId(String facebookId) {
        return jdbcTemplate.queryForObject(SQL_GET_USER_COUNT_BY_FB_ID, Integer.class, facebookId);
    }

    @Override
    public User getUserById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_GET_USER_BY_ID, userRowMapper, userId);
    }

    @Override
    public User getUserByFacebookId(String facebookId) {
        return jdbcTemplate.queryForObject(SQL_GET_USER_BY_FB_ID, userRowMapper, facebookId);
    }
}

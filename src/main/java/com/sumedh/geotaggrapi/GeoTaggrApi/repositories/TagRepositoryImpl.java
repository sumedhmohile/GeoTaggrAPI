package com.sumedh.geotaggrapi.GeoTaggrApi.repositories;

import com.sumedh.geotaggrapi.GeoTaggrApi.domain.Tag;
import com.sumedh.geotaggrapi.GeoTaggrApi.exceptions.GTAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private final String SQL_CREATE_TAG = "INSERT INTO GT_TAGS(TAG_ID, TAG_BODY, SET_FOR_ID, SET_BY_ID, LATITUDE, LONGITUDE) VALUES(NEXTVAL('GT_TAG_SEQ'), ?, ?, ?, ?, ?)";
    private final String SQL_GET_TAG_BY_ID = "SELECT TAG_ID, TAG_BODY, SET_FOR_ID, SET_BY_ID, LATITUDE, LONGITUDE FROM GT_TAGS WHERE TAG_ID=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Tag> tagRowMapper = ((rs, rowNum) -> new Tag(rs.getInt("TAG_ID"),
                                                                    rs.getString("TAG_BODY"),
                                                                    rs.getString("SET_FOR_ID"),
                                                                    rs.getString("SET_BY_ID"),
                                                                    rs.getDouble("LATITUDE"),
                                                                    rs.getDouble("LONGITUDE")
                                                                ));

    @Override
    public Integer createTag(String tagText, String setById, String setForId, Double latitude, Double longitude) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_TAG, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, tagText);
                ps.setString(2, setForId);
                ps.setString(3, setById);
                ps.setDouble(4, latitude);
                ps.setDouble(5, longitude);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("TAG_ID");
        } catch (Exception e) {
            e.printStackTrace();
            throw new GTAuthException("Unable to create new tag");
        }
    }

    @Override
    public void updateTag(Integer tagId, String tagText) {

    }

    @Override
    public void delete(Integer tagId) {

    }

    @Override
    public List<Tag> getAllTagsSetForUser(String setForId) {
        return null;
    }

    @Override
    public List<Tag> getAllTagsSetByUserForOthers(String setById) {
        return null;
    }

    @Override
    public Tag getTagById(Integer id) {
        return jdbcTemplate.queryForObject(SQL_GET_TAG_BY_ID, tagRowMapper, id);
    }
}

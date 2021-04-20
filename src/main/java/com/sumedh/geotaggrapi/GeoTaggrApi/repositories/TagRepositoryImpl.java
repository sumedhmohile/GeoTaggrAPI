package com.sumedh.geotaggrapi.GeoTaggrApi.repositories;

import com.sumedh.geotaggrapi.GeoTaggrApi.domain.Tag;
import com.sumedh.geotaggrapi.GeoTaggrApi.exceptions.GTAuthException;
import com.sumedh.geotaggrapi.GeoTaggrApi.exceptions.GTResourceNotFoundException;
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
    private final String SQL_UPDATE_TAG_BY_ID = "UPDATE GT_TAGS SET TAG_BODY=? WHERE TAG_ID=? AND SET_BY_ID=?";
    private final String SQL_DELETE_TAG_BY_ID = "DELETE FROM GT_TAGS WHERE TAG_ID=? AND SET_BY_ID=?";
    private final String SQL_GET_ALL_TAGS_FOR_USER = "SELECT TAG_ID, TAG_BODY, SET_FOR_ID, SET_BY_ID, LATITUDE, LONGITUDE FROM GT_TAGS WHERE SET_FOR_ID=?";
    private final String SQL_GET_ALL_TAGS_BY_USER_FOR_OTHERS = "SELECT TAG_ID, TAG_BODY, SET_FOR_ID, SET_BY_ID, LATITUDE, LONGITUDE FROM GT_TAGS WHERE SET_BY_ID=? AND SET_FOR_ID!=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Tag> tagRowMapper = ((rs, rowNum) -> new Tag(rs.getInt("TAG_ID"),
                                                                    rs.getString("TAG_BODY"),
                                                                    rs.getString("SET_BY_ID"),
                                                                    rs.getString("SET_FOR_ID"),
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
    public void updateTag(Integer tagId, String tagText, String setById) {
        int count = jdbcTemplate.update(SQL_UPDATE_TAG_BY_ID, tagText, tagId, setById);

        if(count == 0) throw new GTResourceNotFoundException("No such tag");
    }

    @Override
    public void delete(Integer tagId, String setById) {
        int count = jdbcTemplate.update(SQL_DELETE_TAG_BY_ID, tagId, setById);

        if(count == 0) throw new GTResourceNotFoundException("No such tag");
    }

    @Override
    public List<Tag> getAllTagsSetForUser(String setForId) {
        return jdbcTemplate.query(SQL_GET_ALL_TAGS_FOR_USER, tagRowMapper, setForId);
    }

    @Override
    public List<Tag> getAllTagsSetByUserForOthers(String setById) {
        return jdbcTemplate.query(SQL_GET_ALL_TAGS_BY_USER_FOR_OTHERS, tagRowMapper, setById, setById);
    }

    @Override
    public Tag getTagById(Integer id) {
        return jdbcTemplate.queryForObject(SQL_GET_TAG_BY_ID, tagRowMapper, id);
    }
}

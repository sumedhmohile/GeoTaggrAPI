package com.sumedh.geotaggrapi.GeoTaggrApi.repositories;

import com.sumedh.geotaggrapi.GeoTaggrApi.domain.Tag;

import java.util.List;

public interface TagRepository {
    Integer createTag(String tagText, String setById, String setForId, Double latitude, Double longitude);

    void updateTag(Integer tagId, String tagText, String setById);

    void delete(Integer tagId, String setById);

    List<Tag> getAllTagsSetForUser(String setForId);

    List<Tag> getAllTagsSetByUserForOthers(String setById);

    Tag getTagById(Integer id);

}

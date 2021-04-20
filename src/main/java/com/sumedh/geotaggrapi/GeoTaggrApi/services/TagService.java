package com.sumedh.geotaggrapi.GeoTaggrApi.services;

import com.sumedh.geotaggrapi.GeoTaggrApi.domain.Tag;

import java.util.List;

public interface TagService {
    Tag createTag(String tagText, String setById, String setForId, Double latitude, Double longitude);

    void updateTag(Integer tagId, String tagText);

    void delete(Integer tagId);

    List<Tag> getAllTagsSetForUser(String setForId);

    List<Tag> getAllTagsSetByUserForOthers(String setById);
}

package com.sumedh.geotaggrapi.GeoTaggrApi.services;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.sumedh.geotaggrapi.GeoTaggrApi.domain.Tag;

import java.io.IOException;
import java.util.List;

public interface TagService {
    Tag createTag(String tagText, String setById, String setForId, Double latitude, Double longitude) throws FirebaseMessagingException;

    void updateTag(Integer tagId, String tagText, String setById);

    void delete(Integer tagId, String setById);

    List<Tag> getAllTagsSetForUser(String setForId);

    List<Tag> getAllTagsSetByUserForOthers(String setById);
}

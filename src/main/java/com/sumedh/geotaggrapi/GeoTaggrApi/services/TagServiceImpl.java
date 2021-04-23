package com.sumedh.geotaggrapi.GeoTaggrApi.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.sumedh.geotaggrapi.GeoTaggrApi.domain.Tag;
import com.sumedh.geotaggrapi.GeoTaggrApi.domain.User;
import com.sumedh.geotaggrapi.GeoTaggrApi.exceptions.GTResourceNotFoundException;
import com.sumedh.geotaggrapi.GeoTaggrApi.repositories.TagRepository;
import com.sumedh.geotaggrapi.GeoTaggrApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Tag createTag(String tagText, String setById, String setForId, Double latitude, Double longitude) throws FirebaseMessagingException {
        Integer tagId = tagRepository.createTag(tagText, setById, setForId, latitude, longitude);

        Tag tag = tagRepository.getTagById(tagId);

        String fcmToken = userRepository.getUserByFacebookId(setForId).getFcmToken();
        User setBy = userRepository.getUserByFacebookId(setById);

        Message message = Message.builder()
                .putData("tagId", tag.getTagId().toString())
                .putData("tagText", tag.getTagBody())
                .putData("setByName", setBy.getName())
                .putData("setById", setBy.getFacebookId())
                .putData("latitude", tag.getLatitude().toString())
                .putData("longitude", tag.getLongitude().toString())
                .putData("type", "NEW_TAG")
                .setToken(fcmToken)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);

        return tagRepository.getTagById(tagId);
    }

    @Override
    public void updateTag(Integer tagId, String tagText, String setById) {
        tagRepository.updateTag(tagId, tagText, setById);
    }

    @Override
    public void delete(Integer tagId, String setById) {
        tagRepository.delete(tagId, setById);
    }

    @Override
    public List<Tag> getAllTagsSetForUser(String setForId) {
        return tagRepository.getAllTagsSetForUser(setForId);
    }

    @Override
    public List<Tag> getAllTagsSetByUserForOthers(String setById) {
        return tagRepository.getAllTagsSetByUserForOthers(setById);
    }
}

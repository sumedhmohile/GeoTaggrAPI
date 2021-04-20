package com.sumedh.geotaggrapi.GeoTaggrApi.services;

import com.sumedh.geotaggrapi.GeoTaggrApi.domain.Tag;
import com.sumedh.geotaggrapi.GeoTaggrApi.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public Tag createTag(String tagText, String setById, String setForId, Double latitude, Double longitude) {
        Integer tagId = tagRepository.createTag(tagText, setById, setForId, latitude, longitude);

        return tagRepository.getTagById(tagId);
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
}

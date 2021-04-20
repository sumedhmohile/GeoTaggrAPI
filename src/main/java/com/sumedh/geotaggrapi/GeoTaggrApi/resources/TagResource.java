package com.sumedh.geotaggrapi.GeoTaggrApi.resources;

import com.sumedh.geotaggrapi.GeoTaggrApi.domain.Tag;
import com.sumedh.geotaggrapi.GeoTaggrApi.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/tags")
public class TagResource {

    @Autowired
    TagService tagService;

    @PostMapping("")
    public ResponseEntity<Tag> createTag(HttpServletRequest request, @RequestBody Map<String, Object> tagMap) {
        String setById = (String) request.getAttribute("userId");
        String tagText = (String) tagMap.get("tagText");
        String setForId = (String) tagMap.get("setForId");
        Double latitude = (Double) tagMap.get("latitude");
        Double longitude = (Double) tagMap.get("longitude");

        Tag createdTag = tagService.createTag(tagText, setById, setForId, latitude, longitude);

        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

}

package com.sumedh.geotaggrapi.GeoTaggrApi.resources;

import com.sumedh.geotaggrapi.GeoTaggrApi.domain.Tag;
import com.sumedh.geotaggrapi.GeoTaggrApi.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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

    @PostMapping("/{tagId}")
    public ResponseEntity<Map<String, Boolean>> updateTag(HttpServletRequest request, @RequestBody Map<String, Object> tagMap, @PathVariable("tagId") Integer tagId) {
        String setById = (String) request.getAttribute("userId");
        String tagText = (String) tagMap.get("tagText");

        tagService.updateTag(tagId, tagText, setById);

        Map<String, Boolean> response = new HashMap<>();
        response.put("message", true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Map<String, Boolean>> deleteTag(HttpServletRequest request, @PathVariable("tagId") Integer tagId) {
        String setById = (String) request.getAttribute("userId");

        tagService.delete(tagId, setById);

        Map<String, Boolean> response = new HashMap<>();
        response.put("message", true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Tag>> getTagsForUser(HttpServletRequest request) {
        String setForId = (String) request.getAttribute("userId");

        List<Tag> tags = tagService.getAllTagsSetForUser(setForId);

        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/others")
    public ResponseEntity<List<Tag>> getTagsByUserForOthers(HttpServletRequest request) {
        String setById = (String) request.getAttribute("userId");

        List<Tag> tags = tagService.getAllTagsSetByUserForOthers(setById);

        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

}

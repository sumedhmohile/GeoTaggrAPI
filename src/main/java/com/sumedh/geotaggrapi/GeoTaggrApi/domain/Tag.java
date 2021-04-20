package com.sumedh.geotaggrapi.GeoTaggrApi.domain;

public class Tag {
    private Integer tagId;
    private String tagBody;
    private String setById;
    private String setForId;
    private Double latitude;
    private Double longitude;


    public Tag(Integer tagId, String tagBody, String setById, String setForId, Double latitude, Double longitude) {
        this.tagId = tagId;
        this.tagBody = tagBody;
        this.setById = setById;
        this.setForId = setForId;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagBody() {
        return tagBody;
    }

    public void setTagBody(String tagBody) {
        this.tagBody = tagBody;
    }

    public String getSetById() {
        return setById;
    }

    public void setSetById(String setById) {
        this.setById = setById;
    }

    public String getSetForId() {
        return setForId;
    }

    public void setSetForId(String setForId) {
        this.setForId = setForId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}

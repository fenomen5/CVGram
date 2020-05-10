package com.barkov.ais.cvgram.entity;

public class CvInfo {

    private int id;
    private String title;
    private String region;
    private String district;
    private String status;
    private String skills;
    private int views;

    public CvInfo()
    {

    }

    public CvInfo(String title, String region, String district, String status, String skills, int views) {
        this.title = title;
        this.region = region;
        this.district = district;
        this.status = status;
        this.skills = skills;
        this.views = views;
    }

    @Override
    public String toString() {
        return "CvInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", region='" + region + '\'' +
                ", district='" + district + '\'' +
                ", status='" + status + '\'' +
                ", skills='" + skills + '\'' +
                ", views=" + views +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}

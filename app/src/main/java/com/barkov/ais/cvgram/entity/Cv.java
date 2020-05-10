package com.barkov.ais.cvgram.entity;

public class Cv {

    private int id;
    private String title;
    private int regionId;
    private int districtId;
    private int industryId;
    private String skills;
    private int CvStatusId;

    public Cv()
    {

    }

    public Cv(int id, String title, int regionId, int districtId, int industryId, String skills, int cvStatusId) {
        this.id = id;
        this.title = title;
        this.regionId = regionId;
        this.districtId = districtId;
        this.industryId = industryId;
        this.skills = skills;
        CvStatusId = cvStatusId;
    }

    @Override
    public String toString() {
        return "Cv{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", regionId=" + regionId +
                ", districtId=" + districtId +
                ", industryId=" + industryId +
                ", skills='" + skills + '\'' +
                ", CvStatusId=" + CvStatusId +
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

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public int getCvStatusId() {
        return CvStatusId;
    }

    public void setCvStatusId(int cvStatusId) {
        CvStatusId = cvStatusId;
    }
}

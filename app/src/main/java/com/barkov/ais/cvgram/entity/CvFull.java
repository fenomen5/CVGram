package com.barkov.ais.cvgram.entity;

public class CvFull {

    private int id;
    private String title;
    private int regionId;
    private String regionName;
    private int districtId;
    private String districtName;
    private int industryId;
    private String industryName;
    private String skills;
    private int CvStatusId;
    private String CvStatusName;
    private String updated;

    public CvFull()
    {

    }

    public CvFull(int id, String title, int regionId, String regionName, int districtId, String districtName, int industryId, String industryName, String skills, int cvStatusId, String cvStatusName, String updated) {
        this.id = id;
        this.title = title;
        this.regionId = regionId;
        this.regionName = regionName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.industryId = industryId;
        this.industryName = industryName;
        this.skills = skills;
        CvStatusId = cvStatusId;
        CvStatusName = cvStatusName;
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "CvFull{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                ", districtId=" + districtId +
                ", districtName='" + districtName + '\'' +
                ", industryId=" + industryId +
                ", industryName='" + industryName + '\'' +
                ", skills='" + skills + '\'' +
                ", CvStatusId=" + CvStatusId +
                ", CvStatusName='" + CvStatusName + '\'' +
                ", updated='" + updated + '\'' +
                '}';
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
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

    public String getCvStatusName() {
        return CvStatusName;
    }

    public void setCvStatusName(String cvStatusName) {
        CvStatusName = cvStatusName;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}

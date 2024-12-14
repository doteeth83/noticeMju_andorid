package com.example.notice_mju;

public class DepartmentModel {
    private int id;
    private String name;
    private String profileUrl;
    private String org;
    private String univ;

    public DepartmentModel(int id, String name, String profileUrl, String org, String univ) {
        this.id = id;
        this.name = name;
        this.profileUrl = profileUrl;
        this.org = org;
        this.univ = univ;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getOrg() {
        return org;
    }

    public String getUniv() {
        return univ;
    }
}

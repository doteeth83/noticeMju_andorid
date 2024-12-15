package com.example.notice_mju;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "posts")
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id; // 기본 키
    private String description; // 본문
    private String photos; // 사진 경로 (JSON 형식으로 저장)
    private String startDate; // 시작 날짜
    private String endDate; // 종료 날짜

    // 생성자 및 getter/setter
    public Post(String description, String photos, String startDate, String endDate) {
        this.description = description;
        this.photos = photos;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPhotos() { return photos; }
    public void setPhotos(String photos) { this.photos = photos; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}

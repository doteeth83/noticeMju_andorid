package com.example.notice_mju;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {

    // 데이터 삽입
    @Insert
    void insertPost(Post post);

    // 최신순으로 모든 게시글 가져오기
    @Query("SELECT * FROM posts ORDER BY id DESC")
    List<Post> getAllPosts();
}

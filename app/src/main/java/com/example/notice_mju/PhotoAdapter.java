package com.example.notice_mju;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private final List<String> photoList;
    private final OnPhotoRemoveListener removeListener;

    public PhotoAdapter(List<String> photoList, OnPhotoRemoveListener removeListener) {
        this.photoList = photoList;
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        String photoPath = photoList.get(position);

        // 로컬 저장소에 저장된 사진을 Glide로 로드 (file:// 경로 처리)
        File photoFile = new File(photoPath);
        if (photoFile.exists()) {
            Glide.with(holder.itemView.getContext())
                    .load(photoFile)
                    .into(holder.photoImageView);
        }

        holder.removeButton.setOnClickListener(v -> {
            removeListener.onPhotoRemove(position);
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public interface OnPhotoRemoveListener {
        void onPhotoRemove(int position);
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        ImageButton removeButton;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.image_photo);
            removeButton = itemView.findViewById(R.id.button_remove_photo);
        }
    }
}

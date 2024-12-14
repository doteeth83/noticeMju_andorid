package com.example.notice_mju;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreatePostActivity extends AppCompatActivity {

    private ImageView btnUploadPhoto;
    private Button btnStartDate, btnEndDate, btnSubmitPost;
    private EditText editPostDescription;
    private RecyclerView photoRecyclerView;
    private PhotoAdapter photoAdapter;
    private List<String> photoList; // 사진 경로 리스트
    private static final int MAX_PHOTOS = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // 뷰 초기화
        btnUploadPhoto = findViewById(R.id.btn_upload_photo);
        btnStartDate = findViewById(R.id.btn_start_date);
        btnEndDate = findViewById(R.id.btn_end_date);
        btnSubmitPost = findViewById(R.id.btn_submit_post);
        editPostDescription = findViewById(R.id.edit_post_description);
        photoRecyclerView = findViewById(R.id.photo_recycler_view);

        // RecyclerView 설정
        photoList = new ArrayList<>();
        photoAdapter = new PhotoAdapter(photoList, this::removePhoto);
        photoRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        photoRecyclerView.setAdapter(photoAdapter);

        // 사진 업로드 버튼
        btnUploadPhoto.setOnClickListener(v -> {
            if (photoList.size() >= MAX_PHOTOS) {
                Toast.makeText(this, "최대 12장의 사진만 업로드할 수 있습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            openPhotoPicker();
        });

        // 날짜 선택 버튼
        btnStartDate.setOnClickListener(v -> showDatePicker(btnStartDate));
        btnEndDate.setOnClickListener(v -> showDatePicker(btnEndDate));

        // 등록하기 버튼
        btnSubmitPost.setOnClickListener(v -> {
            String description = editPostDescription.getText().toString().trim();
            if (description.isEmpty()) {
                Toast.makeText(this, "본문 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (photoList.isEmpty()) {
                Toast.makeText(this, "최소 1장의 사진을 업로드해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "게시물이 등록되었습니다.", Toast.LENGTH_SHORT).show();
        });
    }

    private void showDatePicker(Button button) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> button.setText(year + "-" + (month + 1) + "-" + dayOfMonth),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void openPhotoPicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 다중 선택
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                // 다중 선택
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    if (photoList.size() < MAX_PHOTOS) {
                        String photoUri = data.getClipData().getItemAt(i).getUri().toString();
                        photoList.add(photoUri);
                    }
                }
            } else if (data.getData() != null) {
                // 단일 선택
                String photoUri = data.getData().toString();
                if (photoList.size() < MAX_PHOTOS) {
                    photoList.add(photoUri);
                }
            }
            photoAdapter.notifyDataSetChanged();
        }
    }

    private void removePhoto(int position) {
        photoList.remove(position);
        photoAdapter.notifyItemRemoved(position);
        Toast.makeText(this, "사진이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
    }
}

package com.example.notice_mju;

import android.app.AlertDialog;
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

        // 뒤로가기 버튼
        Button btnRegisterNotice = findViewById(R.id.btn_prev);
        btnRegisterNotice.setOnClickListener(v -> {
            //  이전 페이지로 이동(현재 액티비티를 종료함)
            finish();
        });
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
            String photos = photoList.isEmpty() ? null : String.join(",", photoList);
            String startDate = btnStartDate.getText().toString();
            String endDate = btnEndDate.getText().toString();

            if (description.isEmpty()) {
                Toast.makeText(this, "본문 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                Post post = new Post(description, photos, startDate, endDate);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().postDao().insertPost(post);

                // Room 데이터 확인 로그 추가
                List<Post> dbPosts = DatabaseClient.getInstance(getApplicationContext())
                        .getAppDatabase()
                        .postDao()
                        .getAllPosts();
                for (Post dbPost : dbPosts) {
                    System.out.println("DB Post: " + dbPost.getDescription());
                }

                // UI 스레드에서 팝업 창 띄우기
                runOnUiThread(() -> showSuccessPopup());
            }).start();
        });


    }
//팝업 창 코드
private void showSuccessPopup() {
    runOnUiThread(() -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // 바깥 클릭으로 닫히지 않게 설정
        builder.setTitle(null); // 타이틀 생략
        builder.setMessage("등록이 완료되었어요");

        // 확인 버튼
        builder.setPositiveButton("확인", (dialog, which) -> {
            // MainActivity로 이동
            Intent intent = new Intent(CreatePostActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // 현재 Activity 종료
        });

        // 팝업 아이콘 설정 (체크 아이콘을 원할 경우 추가)
        builder.setIcon(R.drawable.ic_success); // res/drawable/ic_success.png

        // 다이얼로그 생성 및 표시
        AlertDialog dialog = builder.create();
        dialog.show();
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

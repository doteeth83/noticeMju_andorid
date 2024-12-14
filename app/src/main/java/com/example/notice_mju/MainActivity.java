package com.example.notice_mju;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DepartmentAdapter adapter;
    private List<DepartmentModel> fullDepartmentList;
    private List<DepartmentModel> filteredList;
    private List<DepartmentModel> currentPageList;
    private LinearLayout paginationLayout;
    private LinearLayout univFilterLayout;
    private EditText searchBox; // 검색창 추가
    private int currentPage = 0;
    private static final int ITEMS_PER_PAGE = 9;

    // 배너 관련 필드
    private ViewPager2 bannerViewPager;
    private TabLayout tabLayout;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        // 검색창 및 필터 레이아웃 초기화
        searchBox = findViewById(R.id.search_box);
        univFilterLayout = findViewById(R.id.univ_filter_layout);
        paginationLayout = findViewById(R.id.pagination_layout);

        // 데이터 초기화
        fullDepartmentList = new ArrayList<>();
        populateData();

        filteredList = new ArrayList<>(fullDepartmentList);
        currentPageList = new ArrayList<>();

        // Adapter 설정
        adapter = new DepartmentAdapter(this, currentPageList);
        recyclerView.setAdapter(adapter);

        // 초기 데이터와 UI 설정
        updatePageData();
        setupPaginationButtons();
        setupUnivFilterButtons();
        setupSearchBox();

        // 배너 초기화
        setupBanner();
    }
    private void setupBanner() {
        bannerViewPager = findViewById(R.id.banner_view_pager);
        tabLayout = findViewById(R.id.tab_layout); // TabLayout 초기화
        Button btnRegisterNotice = findViewById(R.id.btn_register_notice);

        // 배너 이미지 리스트 추가
        List<Integer> bannerImages = new ArrayList<>();
        bannerImages.add(R.drawable.banner1); // 배너 이미지 1
        bannerImages.add(R.drawable.banner2); // 배너 이미지 2

        // 배너 어댑터 설정
        BannerAdapter bannerAdapter = new BannerAdapter(this, bannerImages);
        bannerViewPager.setAdapter(bannerAdapter);

        // 공지사항 버튼 클릭 이벤트
        btnRegisterNotice.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreatePostActivity.class); // CreatePostActivity로 이동
            startActivity(intent);
        });

        // TabLayout과 ViewPager2 연결
        new TabLayoutMediator(tabLayout, bannerViewPager, (tab, position) -> {
            // TabIndicator에 대한 설정 필요시 추가 가능
        }).attach();

        // 자동 스크롤 설정
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                int nextItem = (bannerViewPager.getCurrentItem() + 1) % bannerImages.size(); // 다음 배너로 이동
                bannerViewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 4000); // 4초마다 자동 스크롤
            }
        };
        handler.postDelayed(runnable, 4000); // 자동 스크롤 시작
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable); // 핸들러 제거
        }
    }
    /**
     * 현재 페이지 데이터를 업데이트하고 RecyclerView에 반영
     */
    private void updatePageData() {
        currentPageList.clear();
        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, filteredList.size());
        for (int i = start; i < end; i++) {
            currentPageList.add(filteredList.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 페이지네이션 버튼을 생성하고 UI에 추가
     */
    private void setupPaginationButtons() {
        paginationLayout.removeAllViews();
        int totalPages = (int) Math.ceil((double) filteredList.size() / ITEMS_PER_PAGE);

        for (int i = 0; i < totalPages; i++) {
            Button pageButton = new Button(this);
            pageButton.setText(String.valueOf(i + 1));
            pageButton.setAllCaps(false);

            // 버튼 스타일 수정

            pageButton.setBackgroundColor(android.graphics.Color.TRANSPARENT); // 배경 제거
            pageButton.setTextColor(android.graphics.Color.parseColor("#002E66"));
            pageButton.setPadding(0, 0, 0, 0); // 패딩 제거
            pageButton.setTextSize(16); // 텍스트 크기 설정

            final int pageIndex = i;
            pageButton.setOnClickListener(v -> {
                currentPage = pageIndex;
                updatePageData();
            });

            // 레이아웃 매개변수 설정
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 8, 8, 8); // 버튼 간 간격 설정
            pageButton.setLayoutParams(params);

            paginationLayout.addView(pageButton);
        }
    }


    /**
     * 대학 필터 버튼 생성 및 UI 추가
     */
    private void setupUnivFilterButtons() {
        String[] univNames = {"전체", "총학생회", "인문대학", "사회과학대학", "경영대학", "법과대학", "ICT융합대학"};

        for (String univ : univNames) {
            Button univButton = new Button(this);
            univButton.setText(univ);
            univButton.setAllCaps(false);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 8, 8, 8); // 버튼 간 여백 설정
            univButton.setLayoutParams(params);
            univButton.setTextSize(10);
            univButton.setMinimumHeight(2);
            univButton.setMinHeight(0);
            univButton.setBackgroundResource(R.drawable.button_filter_selector);
            univButton.setTextColor(getResources().getColorStateList(R.color.filter_button_text_selector));

            univButton.setOnClickListener(v -> {
                currentPage = 0;
                if (univ.equals("전체")) {
                    filteredList = new ArrayList<>(fullDepartmentList);
                } else {
                    filteredList = filterByUniv(univ);
                }
                updatePageData();
                setupPaginationButtons();
            });

            univFilterLayout.addView(univButton);
        }
    }

    /**
     * 검색창 이벤트 설정
     */
    private void setupSearchBox() {
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No-op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase(Locale.ROOT).trim();
                filteredList = filterBySearch(query);
                currentPage = 0; // 검색 시 첫 페이지로 이동
                updatePageData();
                setupPaginationButtons();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No-op
            }
        });
    }

    /**
     * 특정 대학에 속한 데이터를 필터링
     * @param univ 선택된 대학 이름
     * @return 필터링된 데이터 리스트
     */
    private List<DepartmentModel> filterByUniv(String univ) {
        List<DepartmentModel> result = new ArrayList<>();
        for (DepartmentModel model : fullDepartmentList) {
            if (univ.equals(model.getUniv())) {
                result.add(model);
            }
        }
        return result;
    }

    /**
     * 검색어로 데이터를 필터링 (한글, 영어 지원)
     * @param query 검색어
     * @return 필터링된 데이터 리스트
     */
    private List<DepartmentModel> filterBySearch(String query) {
        List<DepartmentModel> result = new ArrayList<>();
        for (DepartmentModel model : fullDepartmentList) {
            if (model.getName().toLowerCase(Locale.ROOT).contains(query) || // 학과 이름
                    model.getOrg().toLowerCase(Locale.ROOT).contains(query) ||  // 소속 기관
                    (model.getUniv() != null && model.getUniv().toLowerCase(Locale.ROOT).contains(query))) { // 소속 대학
                result.add(model);
            }
        }
        return result;
    }


    private void populateData() {
        fullDepartmentList.add(new DepartmentModel(1, "새로", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/saero.jpg", "총학생회", "총학생회"));
        fullDepartmentList.add(new DepartmentModel(2, "비상대책위원회", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/liberalArts.jpg", "인문대학", "인문대학"));
        fullDepartmentList.add(new DepartmentModel(3, "울림", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/socialScience.jpg", "사회과학대학", "사회과학대학"));
        fullDepartmentList.add(new DepartmentModel(4, "IN:sight", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/business.jpg", "경영대학", "경영대학"));
        fullDepartmentList.add(new DepartmentModel(5, "With", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/law.jpg", "법과대학", "법과대학"));
        fullDepartmentList.add(new DepartmentModel(6, "새솔", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/ict.jpg", "ICT융합대학", "ICT융합대학"));
        fullDepartmentList.add(new DepartmentModel(7, "화연", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/korean.jpg", "국어국문학과", "인문대학"));
        fullDepartmentList.add(new DepartmentModel(8, "WAVE", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/english.jpg", "영어영문학과", "인문대학"));
        fullDepartmentList.add(new DepartmentModel(9, "천하중문", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/chinese.jpg", "중어중문학과", "인문대학"));
        fullDepartmentList.add(new DepartmentModel(10, "영원일문", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/japanese.jpg", "일어일문학과", "인문대학"));
        fullDepartmentList.add(new DepartmentModel(11, "작심사일", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/history.jpg", "사학과", "인문대학"));
        fullDepartmentList.add(new DepartmentModel(12, "리브리스", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/lis.jpg", "문헌정보학과", "인문대학"));
        fullDepartmentList.add(new DepartmentModel(13, "누리", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/arab.jpg", "아랍지역학과", "인문대학"));
        fullDepartmentList.add(new DepartmentModel(14, "리버티", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/art.jpg", "미술사학과", "인문대학"));
        fullDepartmentList.add(new DepartmentModel(15, "격동", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/philosophy.jpg", "철학과", "인문대학"));
        fullDepartmentList.add(new DepartmentModel(16, "창조문창", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/cw.jpg", "문예창작학과", "인문대학"));
        fullDepartmentList.add(new DepartmentModel(17, "P.Astel", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/administration.jpg", "행정학과", "사회과학대학"));
        fullDepartmentList.add(new DepartmentModel(18, "X", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/political.jpg", "정치외교학과", "사회과학대학"));
        fullDepartmentList.add(new DepartmentModel(19, "마음", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/child.jpg", "아동학과", "사회과학대학"));
        fullDepartmentList.add(new DepartmentModel(20, "Do:Dream", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/youth.jpg", "청소년지도학과", "사회과학대학"));
        fullDepartmentList.add(new DepartmentModel(21, "DOT(Digitalmedia On Top)", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/digital.jpg", "디지털미디어학과", "사회과학대학"));
        fullDepartmentList.add(new DepartmentModel(22, "비상대책위원회", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/economics.jpg", "경제학과", "사회과학대학"));
        fullDepartmentList.add(new DepartmentModel(23, "크레딧", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/businessDept.jpg", "경영학과", "경영대학"));
        fullDepartmentList.add(new DepartmentModel(24, "비상대책위원회", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/trade.jpg", "국제통상학과", "경영대학"));
        fullDepartmentList.add(new DepartmentModel(25, "비상대책위원회", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/businessInfo.jpg", "경영정보학과", "경영대학"));
        fullDepartmentList.add(new DepartmentModel(26, "비상대책위원회", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/convergence.jpg", "융합소프트웨어학부", "ICT융합대학"));
        fullDepartmentList.add(new DepartmentModel(27, "ID(Insight & Design)", "https://noticemju.s3.ap-northeast-2.amazonaws.com/council/dcd.jpg", "디지털콘텐츠디자인학과", "ICT융합대학"));
    }
}

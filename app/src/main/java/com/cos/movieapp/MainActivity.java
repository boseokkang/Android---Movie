package com.cos.movieapp;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";
    private Context mContext = MainActivity.this;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager; // 스크롤 세로, 가로 결정하는 매니저
    private YtsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Object 초기화
        init();
        // 2. 다운로드 - 레트로핏 연결하기
        initDownload();

        listener();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new YtsAdapter();
    }

    private void initDownload() {
        YtsService ytsService = YtsService.retrofit.create(YtsService.class);
        Call<YtsData> call = ytsService.영화목록가져오기("rating", 10, 1);
        call.enqueue(new Callback<YtsData>() { // callback 받아줌
            @Override
            public void onResponse(Call<YtsData> call, Response<YtsData> response) {
                if (response.isSuccessful() == true) {
                    YtsData ytsData = response.body();
                    // recyclerView 바로 연결
                    adapter.addItems(ytsData.getData().getMovies());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<YtsData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "다운로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listener() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(mContext, "안녕", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
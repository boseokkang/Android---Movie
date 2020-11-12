package com.cos.movieapp;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

// = YtsRepository
public interface YtsService {
    @GET("list_movies.json")
    Call<YtsData> 영화목록가져오기(
            @Query("sort_by") String sort_by,
            @Query("limit") int limit,
            @Query("page") int page
    );

    // onCreate 실행되기 전에 바로 연결하기 위해서 static final로 retrofit 객체를 생성함
    // 나중에 서비스 여러개 만들면 따로 java 파일로 빼기
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://yts.mx/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

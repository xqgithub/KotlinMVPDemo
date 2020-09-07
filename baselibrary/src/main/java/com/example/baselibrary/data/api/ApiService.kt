package com.example.baselibrary.data.api

import com.example.baselibrary.mvp.entity.Translation
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * 客户端接口服务
 */
interface ApiService {
    @GET
    fun getTestData(@Url url: String): Observable<ApiResponse2<Translation>>
}
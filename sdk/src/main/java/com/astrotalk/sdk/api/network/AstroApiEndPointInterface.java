package com.astrotalk.sdk.api.network;

import com.astrotalk.sdk.api.utils.AstroConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AstroApiEndPointInterface {

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AstroConstants.DOMAIN)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("freeAPI/consultant/get-list/filter?")
    Call<ResponseBody> getAstrologerListSorting(
            @Query("appId") int appId,
            @Query("businessId") int businessId,
            @Query("consultantTypeId") int consultantTypeId,
            @Query("timezone") String timezone,
            @Query("userId") long userId,
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize,
            @Query("version") String version,
            @Query("serviceId") int serviceId,
            @Query("languageId") int languageId,
            @Query("sortByExperience") boolean sortByExperience,
            @Query("sortByPrice") boolean sortByPrice,
            @Query("sortByRating") boolean sortByRating,
            @Query("sortByOrder") boolean sortByOrder
    );

}

package com.astrotalk.sdk.api.network

import com.astrotalk.sdk.api.model.AstrologerListResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitInterface {

    @GET("freeAPI/consultant/get-list/filter?")
    fun getAstrologerListSorting(
        @Query("appId") appId: Int,
        @Query("businessId") businessId: Int,
        @Query("consultantTypeId") consultantTypeId: Long,
        @Query("timezone") timezone: String?,
        @Query("userId") userId: Long,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int,
        @Query("version") version: String?,
        @Query("serviceId") serviceId: Long,
        @Query("isDesc") isDesc: Boolean,
        @Query("sortByExperience") sortByExperience: Boolean,
        @Query("sortByPrice") sortByPrice: Boolean,
        @Query("languageId") languageId: Long,
        @Query("sortByRating") sortByRating: Boolean,
        @Query("hardwareId") hardwareId: String?,
        @Query("countryCode") countryCode: String?,
        @Query("sortByOrder") sortByOrder: Boolean?,
        @Query("isOfferV3") isOfferV3: Boolean?,
        @Query("isPoAstrologer") isPoAstrologer: Boolean?,
        @Query("categoryId") categoryId: Int?
    ): Call<AstrologerListResponseModel>

}
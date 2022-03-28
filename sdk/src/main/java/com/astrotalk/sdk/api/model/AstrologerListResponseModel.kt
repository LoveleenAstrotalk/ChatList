package com.astrotalk.sdk.api.model


import com.google.gson.annotations.SerializedName

data class AstrologerListResponseModel(@SerializedName("totalPages")
                                       val totalPages: Int = 0,
                                       @SerializedName("content")
                                       val content: List<ContentItem>?,
                                       @SerializedName("status")
                                       val status: String = "",
                                       @SerializedName("totalElements")
                                       val totalElements: Int = 0)


data class ContentItem(@SerializedName("next")
                       val next: String = "",
                       @SerializedName("introVideo")
                       val introVideo: String = "",
                       @SerializedName("rating")
                       val rating: Double = 0.0,
                       @SerializedName("fo")
                       val fo: Int = 0,
                       @SerializedName("netPriceAfterOffer")
                       val netPriceAfterOffer: Int = 0,
                       @SerializedName("pic")
                       val pic: String = "",
                       @SerializedName("isIntroVideoActive")
                       val isIntroVideoActive: Boolean = false,
                       @SerializedName("visibleForChatLocal")
                       val visibleForChatLocal: Boolean = false,
                       @SerializedName("price")
                       val price: Int = 0,
                       @SerializedName("skill")
                       val skill: String = "",
                       @SerializedName("wtForegin")
                       val wtForegin: Int = 0,
                       @SerializedName("id")
                       val id: Int = 0,
                       @SerializedName("exp")
                       val exp: String = "",
                       @SerializedName("lang")
                       val lang: String = "",
                       @SerializedName("wt")
                       val wt: Int = 0,
                       @SerializedName("picId")
                       val picId: String = "",
                       @SerializedName("order")
                       val order: Int = 0,
                       @SerializedName("isPoSo")
                       val isPoSo: Boolean = false,
                       @SerializedName("languages")
                       val languages: List<LanguagesItem>?,
                       @SerializedName("statusChat")
                       val statusChat: String = "",
                       @SerializedName("tick")
                       val tick: Boolean = false,
                       @SerializedName("isNew")
                       val isNew: Boolean = false,
                       @SerializedName("nextCall")
                       val nextCall: String = "",
                       @SerializedName("religion")
                       val religion: List<String>?,
                       @SerializedName("statusCall")
                       val statusCall: String = "",
                       @SerializedName("statusVideoCall")
                       val statusVideoCall: String = "",
                       @SerializedName("nextChat")
                       val nextChat: String = "",
                       @SerializedName("problemArea")
                       val problemArea: String = "",
                       @SerializedName("name")
                       val name: String = "",
                       @SerializedName("isPo")
                       val isPo: Boolean = false,
                       @SerializedName("waitlistSizeChat")
                       val waitlistSizeChat: Int = 0,
                       @SerializedName("nextVideoCall")
                       val nextVideoCall: String = "",
                       @SerializedName("status")
                       val status: String = "")

data class LanguagesItem(@SerializedName("languageId")
                         val languageId: Int = 0,
                         @SerializedName("language")
                         val language: String = "")





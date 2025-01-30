package com.lnavarro.rickmorty.data.model.response

import com.google.gson.annotations.SerializedName
import com.lnavarro.rickmorty.data.model.CharacterModel

data class CharacterResponseModel(
    val info: PageInfoModel,
    @SerializedName("results") val characters: List<CharacterModel>
)

data class PageInfoModel(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
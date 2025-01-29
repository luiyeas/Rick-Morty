package com.lnavarro.rickmorty.data.model

import com.google.gson.annotations.SerializedName
import com.lnavarro.rickmorty.domain.model.CharacterDomain

data class CharacterModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginModel,
    val location: LocationModel,
    val image: String,
    val episode: List<String>,
    val url: String,
    @SerializedName("created") val createdAt: String
)

data class OriginModel(
    val name: String,
    val url: String
)

data class LocationModel(
    val name: String,
    val url: String
)

fun CharacterModel.toDomain(): CharacterDomain {
    return CharacterDomain(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        origin = origin.name,
        location = location.name,
        image = image,
        episodesCount = episode.size
    )
}
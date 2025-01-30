package com.lnavarro.rickmorty.domain.model

import com.lnavarro.rickmorty.ui.model.CharacterUI

data class CharacterDomain(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String,
    val episodesCount: Int
)


fun CharacterDomain.toUI(): CharacterUI =
    CharacterUI(id = id, name = name, status = status, species = species, image = image)
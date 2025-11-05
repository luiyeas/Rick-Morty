package com.lnavarro.rickmorty.ui.model

import com.lnavarro.rickmorty.domain.model.CharacterSpecies
import kotlinx.serialization.Serializable

@Serializable
data class CharacterUI(
    val id: Int,
    val name: String,
    val status: String,
    val species: CharacterSpecies,
    val image: String
)


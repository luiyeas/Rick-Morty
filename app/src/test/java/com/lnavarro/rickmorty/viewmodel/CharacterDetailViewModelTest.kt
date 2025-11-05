package com.lnavarro.rickmorty.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.lnavarro.rickmorty.domain.model.CharacterSpecies
import com.lnavarro.rickmorty.ui.model.CharacterUI
import com.lnavarro.rickmorty.ui.screens.characterdetail.CharacterDetailViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterDetailViewModelTest {

    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        val characterUI = CharacterUI(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = CharacterSpecies.HUMAN,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        )
        val characterJson = Json.encodeToString(characterUI)
        savedStateHandle = SavedStateHandle().apply {
            set("characterJson", characterJson)
        }
        viewModel = CharacterDetailViewModel(savedStateHandle)
    }

    @Test
    fun `characterUI is correctly deserialized from savedStateHandle`() {
        val expectedCharacterUI = CharacterUI(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = CharacterSpecies.HUMAN,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        )
        assertEquals(expectedCharacterUI, viewModel.characterUI)
    }
}
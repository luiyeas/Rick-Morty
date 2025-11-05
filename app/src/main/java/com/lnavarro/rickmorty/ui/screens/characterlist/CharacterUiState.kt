package com.lnavarro.rickmorty.ui.screens.characterlist

import com.lnavarro.rickmorty.ui.model.CharacterUI

// Sealed class to manage UI states
sealed class CharacterListState {
    object Loading : CharacterListState()
    data class Success(val characters: List<CharacterUI>) : CharacterListState()
    data class Error(val message: String) : CharacterListState()
}
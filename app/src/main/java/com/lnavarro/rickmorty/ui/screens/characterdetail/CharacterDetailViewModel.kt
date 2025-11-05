package com.lnavarro.rickmorty.ui.screens.characterdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lnavarro.rickmorty.ui.model.CharacterUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val characterUI: CharacterUI = Json.decodeFromString(URLDecoder.decode(savedStateHandle.get<String>("characterJson") ?: "", StandardCharsets.UTF_8.toString()))

}
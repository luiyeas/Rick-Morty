package com.lnavarro.rickmorty.ui.screens.characterdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val characterId: Int = savedStateHandle.get<Int>("characterId") ?: 0

    init {
        Log.d("", "")
    }

}
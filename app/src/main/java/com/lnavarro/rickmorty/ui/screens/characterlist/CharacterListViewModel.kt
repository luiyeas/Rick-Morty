package com.lnavarro.rickmorty.ui.screens.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lnavarro.rickmorty.domain.model.toUI
import com.lnavarro.rickmorty.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    // UI State with sealed class
    private val _uiState = MutableStateFlow<CharacterListState>(CharacterListState.Loading)
    val uiState: StateFlow<CharacterListState> = _uiState.asStateFlow()

    init {
        loadCharacters(0)
    }

    private fun loadCharacters(page: Int) {
        viewModelScope.launch {
            _uiState.value = CharacterListState.Loading
            getCharactersUseCase(page).collect { result ->
                _uiState.value = if (result.isEmpty()) {
                    CharacterListState.Error("Error loading characters.")
                } else {
                    CharacterListState.Success(result.map { it.toUI() })
                }
            }
        }
    }

}
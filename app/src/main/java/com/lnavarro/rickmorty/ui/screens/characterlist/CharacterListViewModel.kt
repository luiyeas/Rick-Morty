package com.lnavarro.rickmorty.ui.screens.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.lnavarro.rickmorty.domain.model.toUI
import com.lnavarro.rickmorty.domain.usecase.GetCharactersUseCase
import com.lnavarro.rickmorty.ui.model.CharacterUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    // UI State with sealed class
    val characters: Flow<PagingData<CharacterUI>> =
        getCharactersUseCase().map { pagingData -> pagingData.map { it.toUI() } }
            .cachedIn(viewModelScope)

}
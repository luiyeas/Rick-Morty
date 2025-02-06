package com.lnavarro.rickmorty.ui.screens.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.lnavarro.rickmorty.domain.model.CharacterSpecies
import com.lnavarro.rickmorty.domain.model.toUI
import com.lnavarro.rickmorty.domain.usecase.GetCharactersUseCase
import com.lnavarro.rickmorty.ui.model.CharacterUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    // StateFlow to hold the current status filter
    private val _statusFilter = MutableStateFlow<String?>(null)
    val statusFilter: StateFlow<String?> = _statusFilter

    private val _selectedFilter = MutableLiveData<CharacterSpecies?>(null)
    var selectedFilter: LiveData<CharacterSpecies?> = _selectedFilter

    // UI State with sealed class
    // Characters flow that depends on the statusFilter
    @OptIn(ExperimentalCoroutinesApi::class)
    val characters: StateFlow<PagingData<CharacterUI>> = _statusFilter.flatMapLatest { filter ->
        getCharactersUseCase(filter).map { pagingData -> pagingData.map { it.toUI() } }
    }.cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    fun onHumanoidClick() {
        _statusFilter.value = CharacterSpecies.HUMANOID.value
        _selectedFilter.value = CharacterSpecies.HUMANOID
    }

    fun onHumanClick() {
        _statusFilter.value = CharacterSpecies.HUMAN.value
        _selectedFilter.value = CharacterSpecies.HUMAN
    }

    fun onUnknownClick() {
        _statusFilter.value = CharacterSpecies.UNKNOWN.value
        _selectedFilter.value = CharacterSpecies.UNKNOWN
    }

    fun onAlienClick() {
        _statusFilter.value = CharacterSpecies.ALIEN.value
        _selectedFilter.value = CharacterSpecies.ALIEN
    }

}
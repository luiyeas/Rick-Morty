package com.lnavarro.rickmorty.ui.screens.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
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
import kotlinx.coroutines.flow.combine
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

    private val _selectedFilter = MutableLiveData<CharacterSpecies?>(null)
    var selectedFilter: LiveData<CharacterSpecies?> = _selectedFilter

    private val _searchText = MutableLiveData<String?>(null)
    var searchText: LiveData<String?> = _searchText

    // UI State with sealed class
    // Characters flow that depends on the statusFilter
    @OptIn(ExperimentalCoroutinesApi::class)
    val characters: StateFlow<PagingData<CharacterUI>> = combine(
        _statusFilter, _searchText.asFlow()
    ) { statusFilter, searchText ->
        // Combine two values in one
        statusFilter to searchText
    }.flatMapLatest { (statusFilter, searchText) ->
        getCharactersUseCase(
            statusFilter, searchText
        ).map { pagingData -> pagingData.map { it.toUI() } }
    }.cachedIn(viewModelScope).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PagingData.empty()
    )

    private fun toggleFilter(species: CharacterSpecies) {
        if (_selectedFilter.value == species) {
            _statusFilter.value = null
            _selectedFilter.value = null
        } else {
            _statusFilter.value = species.value
            _selectedFilter.value = species
        }
    }

    fun onHumanoidClick() = toggleFilter(CharacterSpecies.HUMANOID)

    fun onHumanClick() = toggleFilter(CharacterSpecies.HUMAN)

    fun onUnknownClick() = toggleFilter(CharacterSpecies.UNKNOWN)

    fun onAlienClick() = toggleFilter(CharacterSpecies.ALIEN)

    fun onSearchValueChange(searchText: String) {
        _searchText.value = searchText.ifEmpty { null }
    }

}
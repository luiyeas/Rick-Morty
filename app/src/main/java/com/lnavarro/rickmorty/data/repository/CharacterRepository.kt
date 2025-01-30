package com.lnavarro.rickmorty.data.repository

import com.lnavarro.rickmorty.data.model.toDomain
import com.lnavarro.rickmorty.data.network.RickAndMortyApiClient
import com.lnavarro.rickmorty.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(private val apiService: RickAndMortyApiClient) {

    fun getCharacters(page: Int): Flow<List<CharacterDomain>> = flow {
        try {
            val response = apiService.getAllCharacters()
            val characters = response.characters.map { it.toDomain() }
            emit(characters)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

}
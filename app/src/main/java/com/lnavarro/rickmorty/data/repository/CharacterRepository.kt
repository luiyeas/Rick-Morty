package com.lnavarro.rickmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lnavarro.rickmorty.data.network.RickAndMortyApiClient
import com.lnavarro.rickmorty.data.paging.CharacterPagingSource
import com.lnavarro.rickmorty.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(private val apiService: RickAndMortyApiClient) {

    fun getCharactersPaged(): Flow<PagingData<CharacterDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(apiService) }
        ).flow
    }

}
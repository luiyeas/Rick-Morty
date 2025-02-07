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

    companion object PagingConstants {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 5
    }

    val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE, enablePlaceholders = false
    )

    fun getCharactersPaged(status: String?, name: String?): Flow<PagingData<CharacterDomain>> {
        return Pager(config = pagingConfig,
            pagingSourceFactory = { CharacterPagingSource(apiService, status, name) }).flow
    }

}
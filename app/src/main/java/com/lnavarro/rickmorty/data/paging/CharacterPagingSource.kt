package com.lnavarro.rickmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lnavarro.rickmorty.data.model.toDomain
import com.lnavarro.rickmorty.data.network.RickAndMortyApiClient
import com.lnavarro.rickmorty.domain.model.CharacterDomain
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(private val apiService: RickAndMortyApiClient) :
    PagingSource<Int, CharacterDomain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDomain> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getAllCharacters(page)
            val characters = response.characters.map { it.toDomain() }
            LoadResult.Page(
                data = characters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.info.next != null) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterDomain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
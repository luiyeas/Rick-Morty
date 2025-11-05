package com.lnavarro.rickmorty.remoteservice

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lnavarro.rickmorty.data.model.CharacterModel
import com.lnavarro.rickmorty.data.model.LocationModel
import com.lnavarro.rickmorty.data.model.OriginModel
import com.lnavarro.rickmorty.data.model.response.CharacterResponseModel
import com.lnavarro.rickmorty.data.model.response.PageInfoModel
import com.lnavarro.rickmorty.data.network.RickAndMortyApiClient
import com.lnavarro.rickmorty.data.paging.CharacterPagingSource
import com.lnavarro.rickmorty.domain.model.CharacterDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterPagingSourceTest {

    @MockK
    lateinit var mockApiService: RickAndMortyApiClient

    private lateinit var pagingSource: CharacterPagingSource

    @Before
    fun setUp() {
        // Initialize mockk
        MockKAnnotations.init(this, relaxUnitFun = true)

        // Instantiate the paging source with the mocked service
        pagingSource = CharacterPagingSource(mockApiService)
    }


    @Test
    fun `load returns page when successful with next page`() = runTest {
        // Given: A fake response with next info not null
        val currentPage = 1
        val fakeCharacters = listOf(
            CharacterModel(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                image = "",
                type = "",
                gender = "",
                origin = OriginModel(name = "", url = ""),
                location = LocationModel(name = "", url = ""),
                episode = listOf(),
                url = "",
                createdAt = ""
            )
        )
        val fakeInfo = PageInfoModel(count = 826, pages = 42, next = "url", prev = null)
        val fakeApiResponse = CharacterResponseModel(
            info = fakeInfo, characters = fakeCharacters
        )

        coEvery { mockApiService.getAllCharacters(currentPage) } returns fakeApiResponse

        // When: We call load with LoadParams.Refresh
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = currentPage, loadSize = 20, placeholdersEnabled = false
            )
        )

        // Then: We should get a Page result with a nextKey
        val page = result as? PagingSource.LoadResult.Page
        assertNotNull("Result should be a Page", page)
        assertEquals(
            "Returned data size should match fakeCharacters size",
            fakeCharacters.size,
            page?.data?.size
        )
        // Check that nextKey is currentPage + 1
        assertEquals(
            "Next key should be currentPage + 1", currentPage + 1, page?.nextKey
        )
        // Since currentPage is 1, there is no prevKey
        assertNull(
            "Prev key should be null if currentPage is 1", page?.prevKey
        )
    }

    @Test
    fun `load returns error when exception is thrown`() = runTest {
        // Given: The api service throws an exception
        val currentPage = 3
        coEvery { mockApiService.getAllCharacters(currentPage) } throws RuntimeException("Network error")

        // When: We call load
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = currentPage, loadSize = 20, placeholdersEnabled = false
            )
        )

        // Then: We expect a LoadResult.Error
        val error = result as? PagingSource.LoadResult.Error
        assertNotNull("Result should be an Error", error)
        assertTrue(
            "Cause of the error should be a RuntimeException", error?.throwable is RuntimeException
        )
        assertEquals(
            "Error message should match the thrown exception",
            "Network error",
            error?.throwable?.message
        )
    }

    @Test
    fun `getRefreshKey returns correct key based on anchorPosition - nextKey scenario`() {
        // Given: We have a page whose nextKey is 4
        // We also provide at least one item in data, so the anchorPosition can reference an actual index
        val pages = listOf(
            PagingSource.LoadResult.Page(
                // We include at least 1 item para que anchorPosition pueda coincidir con un índice de la lista
                data = listOf(
                    CharacterDomain(
                        id = 1,
                        name = "Rick Sanchez",
                        status = "Vivo y coleando",
                        species = "Human",
                        image = "",
                        gender = "",
                        origin = "",
                        location = "",
                        episodesCount = 2
                    )
                ), prevKey = 1, nextKey = 3
            )
        )

        // anchorPosition = 0 indicates we are anchored at the first (and only) item of the list
        val pagingState = PagingState(
            pages = pages,
            anchorPosition = 0,
            config = androidx.paging.PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        // When: We call getRefreshKey on our paging source
        val refreshKey = pagingSource.getRefreshKey(pagingState)

        // Then: Given that nextKey = 4, our logic (nextKey - 1) expects 3
        assertEquals(
            "Refresh key should be 2 if closest page has nextKey = 3", 2, refreshKey
        )
    }

    @Test
    fun `getRefreshKey returns null when anchorPosition is null`() {
        // Given: A PagingState with no anchorPosition (anchorPosition = null)
        val pagingState = PagingState<Int, CharacterDomain>(
            pages = emptyList(),
            anchorPosition = null,
            config = androidx.paging.PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        // When: We call getRefreshKey
        val refreshKey = pagingSource.getRefreshKey(pagingState)

        // Then: We expect null
        assertNull("Refresh key should be null if anchorPosition is null", refreshKey)
    }

}
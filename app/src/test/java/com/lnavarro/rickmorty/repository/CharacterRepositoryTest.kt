package com.lnavarro.rickmorty.repository

import com.lnavarro.rickmorty.data.network.RickAndMortyApiClient
import com.lnavarro.rickmorty.data.repository.CharacterRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterRepositoryTest {

    @MockK
    lateinit var apiService: RickAndMortyApiClient

    private lateinit var repository: CharacterRepository

    @Before
    fun setUp() {
        // Initialize the mocks with JUnit 4
        MockKAnnotations.init(this, relaxUnitFun = true)

        repository = CharacterRepository(apiService)
    }

    @Test
    fun `getCharactersPaged should return Flow of PagingData`() = runTest {
        // Given: The repository is set with a mocked apiService
        // When: We call getCharactersPaged()
        val resultFlow = repository.getCharactersPaged()

        // Then: We expect it emits PagingData
        val pagingData = resultFlow.first()
        assertNotNull("PagingData should not be null", pagingData)
    }

    @Test
    fun `pagingConfig should have expected values`() {
        // When: We look at pagingConfig
        val config = repository.pagingConfig

        // Then: We verify the configuration
        assert(config.pageSize == 20) { "pageSize should be 20" }
        assert(config.prefetchDistance == 5) { "prefetchDistance should be 5" }
        assert(!config.enablePlaceholders) { "enablePlaceholders should be false" }
    }

}
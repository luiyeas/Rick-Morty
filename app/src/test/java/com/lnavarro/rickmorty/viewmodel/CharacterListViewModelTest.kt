package com.lnavarro.rickmorty.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import com.lnavarro.rickmorty.domain.model.CharacterDomain
import com.lnavarro.rickmorty.domain.model.toUI
import com.lnavarro.rickmorty.domain.usecase.GetCharactersUseCase
import com.lnavarro.rickmorty.ui.model.CharacterUI
import com.lnavarro.rickmorty.ui.screens.characterlist.CharacterListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description


class CharacterListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    // This callback does nothing, but it is required by AsyncPagingDataDiffer to handle list changes
    private val noOpListCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    // A basic DiffUtil.ItemCallback for CharacterUI to compare items by ID or entire fields
    private val characterUIDiffCallback = object : DiffUtil.ItemCallback<CharacterUI>() {
        override fun areItemsTheSame(oldItem: CharacterUI, newItem: CharacterUI): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: CharacterUI, newItem: CharacterUI): Boolean {
            return oldItem == newItem
        }
    }

    private val domainCharacters = listOf(
        CharacterDomain(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "Earth",
            location = "Earth",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episodesCount = 51
        ),
        CharacterDomain(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "Earth",
            location = "Earth",
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            episodesCount = 51
        )
    )

    @Before
    fun setUp() {
        // Create a mock for the GetCharactersUseCase
        getCharactersUseCase = mockk()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when viewModel characters is collected with differ, it maps domain to UI`() = runTest {
        // Mock the use case to return a PagingData flow with the predefined domainCharacters
        coEvery { getCharactersUseCase() } returns flowOf(PagingData.from(domainCharacters))
        val viewModel = CharacterListViewModel(getCharactersUseCase)

        // Collect the flow using Turbine
        viewModel.characters.test {
            val pagingData = awaitItem()

            // Simulate an adapter consumption with AsyncPagingDataDiffer
            val differ = AsyncPagingDataDiffer(
                diffCallback = characterUIDiffCallback,
                updateCallback = noOpListCallback,
                mainDispatcher = Dispatchers.Main,  // Injected by MainDispatcherRule
                workerDispatcher = Dispatchers.IO
            )

            differ.submitData(pagingData)
            advanceUntilIdle() // Wait until everything is processed

            val uiItems = differ.snapshot().items
            val expected = domainCharacters.map { it.toUI() }
            assertEquals(expected, uiItems)

            // Ensure there are no more emissions
            expectNoEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when useCase returns empty paging data, viewModel emits empty list`() = runTest {
        // Mock the use case to return an empty PagingData flow
        coEvery { getCharactersUseCase() } returns flowOf(PagingData.empty())

        val viewModel = CharacterListViewModel(getCharactersUseCase)

        // Collect the flow using Turbine
        viewModel.characters.test {
            val pagingData = awaitItem()

            // Use AsyncPagingDataDiffer to convert PagingData to a list
            val differ = AsyncPagingDataDiffer(
                diffCallback = characterUIDiffCallback,
                updateCallback = noOpListCallback,
                mainDispatcher = Dispatchers.Main,
                workerDispatcher = Dispatchers.IO
            )

            differ.submitData(pagingData)
            advanceUntilIdle()

            // Verify that the list is empty
            assertEquals(0, differ.itemCount)
            expectNoEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `multiple domain items are properly mapped to UI`() = runTest {
        // Mock the use case to return domainCharacters
        coEvery { getCharactersUseCase() } returns flowOf(PagingData.from(domainCharacters))

        val viewModel = CharacterListViewModel(getCharactersUseCase)

        // Collect the flow using Turbine
        viewModel.characters.test {
            val pagingData = awaitItem()

            // Convert the PagingData to a list using AsyncPagingDataDiffer
            val differ = AsyncPagingDataDiffer(
                diffCallback = characterUIDiffCallback,
                updateCallback = noOpListCallback,
                mainDispatcher = Dispatchers.Main,
                workerDispatcher = Dispatchers.IO
            )
            differ.submitData(pagingData)
            advanceUntilIdle()

            // Compare the items mapped to UI with the expected results
            val result = differ.snapshot().items
            val expected = domainCharacters.map { it.toUI() }

            assertEquals(expected, result)
            expectNoEvents()
        }
    }
}

// Rule that sets a TestDispatcher as the Main dispatcher before each test, and resets it afterwards.
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description?) {
        // Set the Main dispatcher to a TestDispatcher
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description?) {
        // Reset Main dispatcher to the original one
        Dispatchers.resetMain()
    }
}
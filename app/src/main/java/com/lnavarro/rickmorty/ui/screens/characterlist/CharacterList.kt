package com.lnavarro.rickmorty.ui.screens.characterlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.lnavarro.rickmorty.ui.components.CharacterCard
import com.lnavarro.rickmorty.ui.model.CharacterUI

@Composable
fun CharacterListScreen(
    modifier: Modifier,
    characterListViewModel: CharacterListViewModel
) {
    val characters = characterListViewModel.characters.collectAsLazyPagingItems()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        when (characters.loadState.refresh) {
            is LoadState.Loading -> {
                CircularProgressIndicator()
            }
            is LoadState.Error -> {
                val errorMessage = (characters.loadState.refresh as LoadState.Error).error.localizedMessage
                Text(
                    text = "Error: $errorMessage",
                    color = Color.Red,
                    fontSize = 18.sp
                )
            }
            else -> {
                CharacterList(characters)
            }
        }
    }
}

@Composable
fun CharacterList(characters: LazyPagingItems<CharacterUI>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        items(characters.itemCount) { index ->
            val character = characters[index]
            character?.let { CharacterCard(characterUI = it) }
        }

        when (characters.loadState.append) {
            is LoadState.Loading -> {
                item { LoadingIndicator() }
            }
            is LoadState.Error -> {
                item { RetryButton { characters.retry() } }
            }
            else -> {}
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun RetryButton(retry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = retry) {
            Text("Reintentar")
        }
    }
}
package com.lnavarro.rickmorty.ui.screens.characterlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lnavarro.rickmorty.ui.components.CharacterCard
import com.lnavarro.rickmorty.ui.model.CharacterUI

@Composable
fun CharacterListScreen(modifier: Modifier, characterListViewModel: CharacterListViewModel) {

    val uiState by characterListViewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is CharacterListState.Loading -> {
                CircularProgressIndicator()
            }

            is CharacterListState.Success -> {
                CharacterList((uiState as CharacterListState.Success).characters)
            }

            is CharacterListState.Error -> {
                val errorMessage = (uiState as CharacterListState.Error).message
                Text(text = "Error: $errorMessage", color = Color.Red, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun CharacterList(characters: List<CharacterUI>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        items(characters) { character ->
            CharacterCard(characterUI = character)
        }
    }
}
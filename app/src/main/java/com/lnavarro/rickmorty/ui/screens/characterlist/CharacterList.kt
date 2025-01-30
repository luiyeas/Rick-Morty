package com.lnavarro.rickmorty.ui.screens.characterlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

@Composable
fun CharacterListScreen(modifier: Modifier, characterListViewModel: CharacterListViewModel) {

    val uiState by characterListViewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is CharacterListState.Loading -> {
                CircularProgressIndicator()
            }

            is CharacterListState.Success -> {
                // Placeholder: Add UI for list later
                Text(text = "Characters loaded successfully!", fontSize = 20.sp)
            }

            is CharacterListState.Error -> {
                val errorMessage = (uiState as CharacterListState.Error).message
                Text(text = "Error: $errorMessage", color = Color.Red, fontSize = 18.sp)
            }
        }
    }
}
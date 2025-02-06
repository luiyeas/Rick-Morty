package com.lnavarro.rickmorty.ui.screens.characterlist

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.lnavarro.rickmorty.R
import com.lnavarro.rickmorty.domain.model.CharacterSpecies
import com.lnavarro.rickmorty.ui.components.CharacterCard
import com.lnavarro.rickmorty.ui.model.CharacterUI
import com.lnavarro.rickmorty.ui.theme.RickColor

@Composable
fun CharacterListScreen(
    modifier: Modifier, characterListViewModel: CharacterListViewModel
) {
    val characters = characterListViewModel.characters.collectAsLazyPagingItems()
    val selectedFilter: CharacterSpecies? by characterListViewModel.selectedFilter.observeAsState(
        initial = null
    )

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
                val errorMessage =
                    (characters.loadState.refresh as LoadState.Error).error.localizedMessage
                Text(
                    text = "Error: $errorMessage", color = Color.Red, fontSize = 18.sp
                )
            }

            else -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    FilterCharacters(selectedFilter, onHumanoidFilterClick = {
                        characterListViewModel.onHumanoidClick()
                    }, onUnknownFilterClick = {
                        characterListViewModel.onUnknownClick()
                    }, onAlienFilterClick = {
                        characterListViewModel.onAlienClick()
                    }, onHumanFilterClick = {
                        characterListViewModel.onHumanClick()
                    })
                    CharacterList(characters)
                }
            }
        }
    }
}

@Composable
fun FilterCharacters(
    selectedFilter: CharacterSpecies?,
    onHumanoidFilterClick: () -> Unit,
    onHumanFilterClick: () -> Unit,
    onAlienFilterClick: () -> Unit,
    onUnknownFilterClick: () -> Unit,

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .horizontalScroll(rememberScrollState())
            .background(RickColor)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Button(
            onClick = { onHumanFilterClick() },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 20.dp,
                pressedElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedFilter == CharacterSpecies.HUMAN) Color.White else RickColor,
                contentColor = if (selectedFilter == CharacterSpecies.HUMAN) RickColor else Color.White
            )
        ) {
            Text(stringResource(id = R.string.filter_button_human))
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = {
                onAlienFilterClick()
            }, elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 20.dp, pressedElevation = 0.dp
            ),  colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedFilter == CharacterSpecies.ALIEN) Color.White else RickColor,
                contentColor = if (selectedFilter == CharacterSpecies.ALIEN) RickColor else Color.White
            )
        ) {
            Text(stringResource(id = R.string.filter_button_alien))
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = {
                onHumanoidFilterClick()
            }, elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 20.dp, pressedElevation = 0.dp
            ),  colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedFilter == CharacterSpecies.HUMANOID) Color.White else RickColor,
                contentColor = if (selectedFilter == CharacterSpecies.HUMANOID) RickColor else Color.White
            )
        ) {
            Text(stringResource(id = R.string.filter_button_humanoid))
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = { onUnknownFilterClick() },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 20.dp, pressedElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedFilter == CharacterSpecies.UNKNOWN) Color.White else RickColor,
                contentColor = if (selectedFilter == CharacterSpecies.UNKNOWN) RickColor else Color.White
            )
        ) {
            Text(stringResource(id = R.string.filter_button_unknown))
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
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun RetryButton(retry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Button(onClick = retry) {
            Text(stringResource(id = R.string.retry_text))
        }
    }
}
package com.lnavarro.rickmorty.ui.screens.characterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.lnavarro.rickmorty.domain.model.CharacterSpecies
import com.lnavarro.rickmorty.ui.components.getStatusColor
import com.lnavarro.rickmorty.ui.components.pixelFont
import com.lnavarro.rickmorty.ui.model.CharacterUI
import com.lnavarro.rickmorty.ui.theme.RickColor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CharacterDetailScreen(
    characterDetailViewModel: CharacterDetailViewModel,
    onBackClick: () -> Unit
) {
    val character = characterDetailViewModel.characterUI

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(RickColor)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = character.name,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            model = ImageRequest.Builder(LocalContext.current).data(character.image).build(),
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            PropertyRow(label = "Status:", value = character.status, color = getStatusColor(character.status))
            PropertyRow(label = "Species:", value = character.species.value)
            PropertyRow(label = "Gender:", value = "Unknown") // Assuming gender is not in CharacterUI yet
        }
    }
}

@Composable
fun PropertyRow(label: String, value: String, color: Color = Color.Black) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge, fontFamily = pixelFont, color = Color.Black)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontFamily = pixelFont, color = color)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
fun CharacterDetailScreenPreview() {
    val sampleCharacter = CharacterUI(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = CharacterSpecies.HUMAN,
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    )
    val characterJson = Json.encodeToString(CharacterUI.serializer(), sampleCharacter)
    val encodedCharacterJson = URLEncoder.encode(characterJson, StandardCharsets.UTF_8.toString())

    val savedStateHandle = SavedStateHandle().apply {
        set("characterJson", encodedCharacterJson)
    }
    val mockViewModel = CharacterDetailViewModel(savedStateHandle)

    CharacterDetailScreen(characterDetailViewModel = mockViewModel, onBackClick = {})
}
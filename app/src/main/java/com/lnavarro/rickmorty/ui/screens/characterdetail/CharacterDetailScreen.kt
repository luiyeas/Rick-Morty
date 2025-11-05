package com.lnavarro.rickmorty.ui.screens.characterdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .build(),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            )
                        )
                    )
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                shape = RoundedCornerShape(32.dp),
                color = getStatusColor(character.status).copy(alpha = 0.9f),
                contentColor = Color.White,
                shadowElevation = 6.dp
            ) {
                Text(
                    text = character.status,
                    style = MaterialTheme.typography.labelLarge,
                    fontFamily = pixelFont,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val propertyItems = listOf(
                CharacterProperty(
                    title = "Status",
                    value = character.status,
                    emoji = "💓",
                    tint = getStatusColor(character.status)
                ),
                CharacterProperty(
                    title = "Species",
                    value = character.species.value,
                    emoji = "🧬",
                    tint = MaterialTheme.colorScheme.primary
                ),
                CharacterProperty(
                    title = "Gender",
                    value = "Unknown",
                    emoji = "🚻",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(propertyItems) { property ->
                    CharacterPropertyCard(property = property)
                }
            }
        }
    }
}

@Composable
private fun CharacterPropertyCard(property: CharacterProperty) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, property.tint.copy(alpha = 0.4f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = property.emoji,
                fontSize = 28.sp
            )
            Text(
                text = property.title.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = property.value,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = pixelFont,
                color = property.tint
            )
        }
    }
}

private data class CharacterProperty(
    val title: String,
    val value: String,
    val emoji: String,
    val tint: Color
)

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

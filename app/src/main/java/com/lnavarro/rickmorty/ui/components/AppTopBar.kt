package com.lnavarro.rickmorty.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.lnavarro.rickmorty.R
import com.lnavarro.rickmorty.ui.theme.RickAndMortyCatalogTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String, onBackClick: (() -> Unit)? = null
) {
    val pixelFont = FontFamily(Font(R.font.pressstartregular))

    TopAppBar(title = { Text(text = title, fontFamily = pixelFont, style = TextStyle(fontSize = 14.sp)) }, navigationIcon = {
        if (onBackClick != null) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary
    )
    )
}

@Preview
@Composable
fun AppTopBarPreview() {
    RickAndMortyCatalogTheme {
        AppTopBar(title = "Rick & Morty")
    }
}
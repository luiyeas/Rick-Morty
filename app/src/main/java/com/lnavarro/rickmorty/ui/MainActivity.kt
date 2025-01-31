package com.lnavarro.rickmorty.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.lnavarro.rickmorty.ui.components.AppTopBar
import com.lnavarro.rickmorty.ui.screens.characterlist.CharacterListScreen
import com.lnavarro.rickmorty.ui.screens.characterlist.CharacterListViewModel
import com.lnavarro.rickmorty.ui.theme.RickAndMortyCatalogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val taskListViewModel: CharacterListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyCatalogTheme {
                Scaffold(topBar = {
                    AppTopBar(title = "I don't know Rick...")
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CharacterListScreen(
                        modifier = Modifier.padding(innerPadding),
                        characterListViewModel = taskListViewModel
                    )
                }
            }
        }
    }
}


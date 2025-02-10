package com.lnavarro.rickmorty.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.lnavarro.rickmorty.ui.components.AppTopBar
import com.lnavarro.rickmorty.ui.navigation.RickAndMortyNavHost
import com.lnavarro.rickmorty.ui.theme.RickAndMortyCatalogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyCatalogTheme {
                Scaffold(topBar = {
                    AppTopBar(title = "I don't know Rick...")
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        RickAndMortyNavHost()
                    }
                }
            }
        }
    }

}


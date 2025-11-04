package com.lnavarro.rickmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lnavarro.rickmorty.ui.screens.characterdetail.CharacterDetailScreen
import com.lnavarro.rickmorty.ui.screens.characterdetail.CharacterDetailViewModel
import com.lnavarro.rickmorty.ui.screens.characterlist.CharacterListScreen
import com.lnavarro.rickmorty.ui.screens.characterlist.CharacterListViewModel
import kotlinx.serialization.json.Json

sealed class Screen(val route: String) {
    data object CharacterList : Screen("characterList")
    data object CharacterDetail : Screen("characterDetail")
}


@Composable
fun RickAndMortyNavHost(
    navController: androidx.navigation.NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController, startDestination = Screen.CharacterList.route
    ) {
        composable(route = Screen.CharacterList.route) {
            val listViewModel = hiltViewModel<CharacterListViewModel>()
            CharacterListScreen(characterListViewModel = listViewModel,
                onCharacterClick = { characterUI ->
                    val characterJson = Json.encodeToString(CharacterUI.serializer(), characterUI)
                    navController.navigate("${Screen.CharacterDetail.route}/$characterJson")
                })
        }
        composable(
            route = "${Screen.CharacterDetail.route}/{characterJson}",
            arguments = listOf(navArgument("characterJson") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val detailViewModel = hiltViewModel<CharacterDetailViewModel>(backStackEntry)
            CharacterDetailScreen(
                characterDetailViewModel = detailViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
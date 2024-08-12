package com.example.noteappcompose.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.noteappcompose.ui.screens.DetailScreen
import com.example.noteappcompose.ui.screens.FavoritesScreen
import com.example.noteappcompose.ui.screens.NoteAddScreen
import com.example.noteappcompose.ui.screens.NoteListScreen

@Composable
fun NavGraph(navController: NavHostController,paddingValues: PaddingValues){


    NavHost(navController = navController, startDestination =Destinations.Home ) {

        composable<Destinations.Home> {
            NoteListScreen(navController, paddingValues =paddingValues)
        }

        composable<Destinations.Favorites> {
            FavoritesScreen(paddingValues = paddingValues)
        }

        composable<Destinations.Detail> {
            val args= it.toRoute<Destinations.Detail>()
            DetailScreen(args,paddingValues,navController)
        }

        composable<Destinations.AddScreen> {
            NoteAddScreen(navController = navController,paddingValues)
        }
    }
}
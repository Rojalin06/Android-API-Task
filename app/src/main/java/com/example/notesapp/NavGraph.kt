package com.example.notesapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun SetupNavGraph(navController: NavHostController) {
    // 1. ViewModel ko NavHost ke bahar define karein taaki ye Shared ho jaye
    val postViewModel: PostViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // --- HOME SCREEN ---
        composable(route = Screen.Home.route) {
            // 2. Shared ViewModel pass karein
            PostListScreen(navController = navController, postViewModel = postViewModel)
        }

        // --- DETAIL SCREEN ---
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId") ?: 0
            // 3. Shared ViewModel yahan bhi pass karein
            DetailScreen(postId = postId, navController = navController, postViewModel = postViewModel)
        }
    }
}
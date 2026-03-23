package com.example.notesapp

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Detail : Screen("detail_screen/{postId}") {
        fun createRoute(postId: Int) = "detail_screen/$postId"
    }
}
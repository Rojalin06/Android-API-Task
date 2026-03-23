package com.example.notesapp

class PostRepository {
    suspend fun getPosts() = RetrofitInstance.api.getPosts()
}
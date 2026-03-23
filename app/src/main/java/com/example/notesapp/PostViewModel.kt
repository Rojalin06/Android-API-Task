package com.example.notesapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val repository = PostRepository()

    // UI state handle karne ke liye
    private val _posts = mutableStateOf<List<Post>>(emptyList())
    val posts: State<List<Post>> = _posts

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // PostViewModel.kt ke andar (Line 18 ke niche add karein)

    // Kisi specific ID ka Post fetch karne ke liye function
    fun getPostById(postId: Int): Post? {
        // Ye line aapki main posts list mein se search karegi
        return _posts.value.find { it.id == postId }
    }
    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun fetchPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            try {
                _posts.value = repository.getPosts()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
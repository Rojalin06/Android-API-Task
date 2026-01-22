package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.ui.theme.NotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = NoteDatabase.getDatabase(this)
        val repository = NoteRepository(database.noteDao())

        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NotesViewModel(repository) as T
            }
        }

        val viewModel = ViewModelProvider(this, viewModelFactory)[NotesViewModel::class.java]

        setContent {
            NotesAppTheme {
                NotesScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(viewModel: NotesViewModel) {
    val notes by viewModel.notes.collectAsState(initial = emptyList())

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("My Notes App (Task 2)") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // --- Input Section ---
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (title.isNotEmpty() && content.isNotEmpty()) {
                        viewModel.addNote(title, content)
                        title = ""
                        content = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Note")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- List Section (Database Data) ---
            LazyColumn {
                items(notes) { note ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                                Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
                            }
                            // Delete Button
                            IconButton(onClick = { viewModel.deleteNote(note) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete Note")
                            }
                        }
                    }
                }
            }
        }
    }
}
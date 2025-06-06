package ai.personal.reader.ui.components.note

import com.arkivanov.decompose.value.Value

interface NotesListComponent {
    val notes: Value<List<Note>>

    // Event handlers or navigation callbacks can be added here later
    fun onNoteClicked(noteId: String)
    fun onAddNoteClicked()
} 
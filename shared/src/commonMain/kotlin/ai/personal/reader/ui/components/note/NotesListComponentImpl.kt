package ai.personal.reader.ui.components.note

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map

class NotesListComponentImpl(
    componentContext: ComponentContext,
    private val onNoteSelected: (noteId: String) -> Unit,
    private val onAddNoteRequested: () -> Unit
) : NotesListComponent, ComponentContext by componentContext {

    private val _notes = MutableValue(initialNotes())
    override val notes: Value<List<Note>> = _notes

    override fun onNoteClicked(noteId: String) {
        onNoteSelected(noteId)
    }

    override fun onAddNoteClicked() {
        onAddNoteRequested()
    }

    private fun initialNotes(): List<Note> = listOf(
        Note("1", "Первая заметка", "Это содержимое первой заметки.", emptyList()),
        Note(
            "2",
            "Вторая заметка",
            "Это содержимое второй заметки. Здесь может быть больше текста.",
            emptyList()
        ),
        Note(
            "3",
            "Заметка с файлом",
            "Эта заметка пока без реального файла, но он мог бы быть здесь.",
            emptyList()
        )
    )

    // You can add functions here to actually add/remove/update notes, perhaps from a database or storage
    fun addNote(note: Note) {
        _notes.map { currentNotes -> currentNotes + note }
    }
} 
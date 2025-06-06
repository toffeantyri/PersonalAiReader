package ai.personal.reader.ui.components.note

import ai.personal.reader.platform.PlatformFile

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val attachedFiles: List<PlatformFile> = emptyList()
) 
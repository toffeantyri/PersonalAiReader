package ai.personal.reader.ui.root.home

import com.arkivanov.decompose.value.Value

interface IHomeComponent {
    val text: Value<String>
    fun onTextChange(newText: String)
} 
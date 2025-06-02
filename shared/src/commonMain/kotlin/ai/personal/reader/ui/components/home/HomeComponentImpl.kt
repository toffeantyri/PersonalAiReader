package ai.personal.reader.ui.root.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class HomeComponentImpl(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, IHomeComponent {
    private val _text = MutableValue("Welcome to Notebook AI Reader!")
    override val text: Value<String> = _text

    override fun onTextChange(newText: String) {
        _text.value = newText
    }
} 
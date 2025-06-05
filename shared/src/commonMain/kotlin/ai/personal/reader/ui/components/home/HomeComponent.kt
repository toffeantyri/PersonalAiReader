package ai.personal.reader.ui.components.home

import com.arkivanov.decompose.value.Value

interface HomeComponent {
    val state: Value<HomeState>

    fun onEvent(event: HomeEvent)
} 
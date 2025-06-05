package ai.personal.reader.ui.components.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class HomeComponentImpl(componentContext: ComponentContext) : HomeComponent,
    ComponentContext by componentContext {

    private val _state = MutableValue(HomeState())
    override val state: Value<HomeState> = _state

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.UpdateQuestion -> {
                _state.value = _state.value.copy(currentQuestion = event.newQuestion)
            }

            HomeEvent.ErrorShown -> {
                _state.value = _state.value.copy(error = null)
            }

            is HomeEvent.LoadDocuments -> {
                // TODO: Implement document loading logic
            }

            is HomeEvent.SelectDocument -> {
                // TODO: Implement document selection logic
            }

            is HomeEvent.AskQuestion -> {
                // TODO: Implement asking question logic
            }

            is HomeEvent.ClearChat -> {
                // TODO: Implement clear chat logic
            }
        }
    }
} 
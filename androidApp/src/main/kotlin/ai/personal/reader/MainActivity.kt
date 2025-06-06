package ai.personal.reader

import ai.personal.reader.theme.AppTheme
import ai.personal.reader.ui.components.root.RootComponent
import ai.personal.reader.ui.components.root.RootComponentImpl
import ai.personal.reader.ui.screens.RootContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.retainedComponent

class MainActivity : ComponentActivity() {

    private lateinit var rootComponent: RootComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.statusBarColor = Color.TRANSPARENT
//        window.navigationBarColor = Color.TRANSPARENT

        rootComponent = retainedComponent { componentContext ->
            RootComponentImpl(
                componentContext = componentContext
            )
        }


        setContent {
            val settingsState by rootComponent.settingsComponent.state.subscribeAsState()
            AppTheme(isDarkTheme = settingsState.isDarkMode) {
                RootContent(rootComponent)
            }
        }
    }
} 
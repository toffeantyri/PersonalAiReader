package ai.personal.reader

import ai.personal.reader.ui.components.root.IRootComponent
import ai.personal.reader.ui.components.root.RootComponentImpl
import ai.personal.reader.ui.screens.RootContent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.retainedComponent

class MainActivity : ComponentActivity() {

    private lateinit var rootComponent: IRootComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        rootComponent = retainedComponent { componentContext ->
            RootComponentImpl(
                componentContext = componentContext
            )
        }


        setContent {
            RootContent(rootComponent)
        }
    }
} 
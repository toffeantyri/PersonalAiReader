package ai.personal.reader

import ai.personal.reader.di.initKoin
import ai.personal.reader.theme.AppTheme
import ai.personal.reader.ui.components.root.RootComponent
import ai.personal.reader.ui.components.root.RootComponentImpl
import ai.personal.reader.ui.screens.RootContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.retainedComponent
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {

    private lateinit var rootComponent: RootComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        initKoin {
            androidContext(this@MainActivity.applicationContext)
        }

        rootComponent = retainedComponent(factory = { componentContext ->
            RootComponentImpl(componentContext, onExitHandle = { finish() })
        })

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootContent(rootComponent)
                }
            }
        }
    }
} 
package ai.personal.reader.context_provider

import android.content.Context
import androidx.startup.Initializer

private lateinit var applicationContext: Context
    private set

internal fun getAppContextByContextProvider(): Context = applicationContext

data object ContextProviderInitializer

class ContextProvider : Initializer<ContextProviderInitializer> {
    override fun create(context: Context): ContextProviderInitializer {
        applicationContext = context.applicationContext
        return ContextProviderInitializer
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
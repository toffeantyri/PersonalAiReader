@file:JvmName("DatabaseModuleAndroid")

package ai.personal.reader.di

import ai.personal.reader.context_provider.getAppContextByContextProvider
import ai.personal.reader.data.database.AppDatabase
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual fun createDb(): AppDatabase {
    val context = getAppContextByContextProvider()
    val dbFile = context.getDatabasePath("personal_ai_reader.db")
    return Room.databaseBuilder<AppDatabase>(
        context = context,
        name = dbFile.absolutePath,
    )
        .setDriver(BundledSQLiteDriver())
        .build()
} 
@file:JvmName("DatabaseModuleJvm")

package ai.personal.reader.di

import ai.personal.reader.data.database.AppDatabase
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

actual fun createDb(): AppDatabase {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "personal_ai_reader.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
//        factory = { AppDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        .build()
} 
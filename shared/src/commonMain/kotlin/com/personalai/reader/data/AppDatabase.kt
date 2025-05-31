package com.personalai.reader.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.ConstructedBy
import kotlinx.coroutines.CoroutineDispatcher

//@Database(entities = [DocumentEntity::class], version = 1)
//@ConstructedBy(AppDatabaseConstructor::class)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun documentDao(): DocumentDao
//}
//
//@Suppress("NO_ACTUAL_FOR_EXPECT")
//expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
//    override fun initialize(): AppDatabase
//}
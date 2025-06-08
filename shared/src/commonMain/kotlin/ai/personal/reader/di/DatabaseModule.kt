package ai.personal.reader.di

import ai.personal.reader.data.database.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun createDb(): AppDatabase

val databaseModule: Module = module {
    single<AppDatabase> { createDb() }
    single { get<AppDatabase>().documentDao() }
    single { get<AppDatabase>().questionDao() }
    single { get<AppDatabase>().answerDao() }
} 
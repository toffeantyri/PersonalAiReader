package ai.personal.reader.di

import ai.personal.reader.data.remote.GroqApi
import ai.personal.reader.data.repository.impl.AiRepositoryImpl
import ai.personal.reader.data.repository.impl.ChatRepositoryImpl
import ai.personal.reader.data.repository.impl.DocumentRepositoryImpl
import ai.personal.reader.domain.repository.AiRepository
import ai.personal.reader.domain.repository.ChatRepository
import ai.personal.reader.domain.repository.DocumentRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { GroqApi(get()) }
    single<AiRepository> { AiRepositoryImpl(get()) }
    single<ChatRepository> { ChatRepositoryImpl(get(), get()) }
    single<DocumentRepository> { DocumentRepositoryImpl(get()) }
} 
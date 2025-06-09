package ai.personal.reader.di

import ai.personal.reader.domain.usecase.AskQuestionUseCase
import ai.personal.reader.domain.usecase.CreateNewChatSessionUseCase
import ai.personal.reader.domain.usecase.GetChatHistoryUseCase
import ai.personal.reader.domain.usecase.LoadDocumentUseCase
import ai.personal.reader.domain.usecase.SaveDocumentUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { AskQuestionUseCase(get(), get()) }
    factory { CreateNewChatSessionUseCase(get()) }
    factory { GetChatHistoryUseCase(get()) }
    factory { LoadDocumentUseCase(get()) }
    factory { SaveDocumentUseCase(get()) }
} 
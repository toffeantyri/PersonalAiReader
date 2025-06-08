//
//  PersonalAiReaderApp.swift
//  PersonalAiReader
//
//  Created by Family on 02.06.2025.
//

import SwiftUI
import shared

@main
struct PersonalAiReaderApp: App {

    init() {
        KoinKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

//
//  ContentView.swift
//  PersonalAiReader
//
//  Created by Family on 02.06.2025.
//

import SwiftUI
import shared // Импортируем общий фреймворк

struct ContentView: View {
    var body: some View {
        ComposeView() // Ваш Compose UI
            .ignoresSafeArea(.all) // Чтобы Compose UI занимал весь экран
    }
}

// Это вспомогательная структура для отображения Compose UI в SwiftUI
struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        // Предполагается, что MainViewControllerKt.MainViewController() доступен в вашем общем модуле
        return MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

#Preview {
    ContentView()
}

//
//  CashTimeApp.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-03-28.
//

import SwiftUI

@main
struct CashTimeApp: App {           //Main-klass? Startar programmet o initialiserar allt
    let viewModel = ViewModel()
    
    var body: some Scene {
        WindowGroup {
            HomeView()
                .environmentObject(viewModel)
        }
    }
}

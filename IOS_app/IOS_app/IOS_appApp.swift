//
//  IOS_appApp.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//
import FirebaseCore
import SwiftUI

@main
struct IOS_appApp: App {
    
    init() {
        FirebaseApp.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            MainView()
        }
    }
}

//
//  CourseView.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct HomeView: View {
    var body: some View {
        NavigationView {
            VStack {
                UserView() // Predpokladám, že tu máš zobrazenie používateľa
                CourseView() // Tento riadok zobrazí kurzy
            }
            .padding()
        }
    }
}

#Preview {
    HomeView()
}

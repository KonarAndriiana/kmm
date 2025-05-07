//
//  CourseView.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct CourseView: View {
    var body: some View {
        VStack(spacing: 24) {
            UserView()
                .accessibilityIdentifier("greeting_text")

            CourseListView()
        }
        .padding()
    }
}

#Preview {
    CourseView()
}

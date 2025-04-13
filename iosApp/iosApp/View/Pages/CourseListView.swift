//
//  CourseListView.swift
//  iosApp
//
//  Created by Andriiana Konar on 14/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CourseListView: View {
    @StateObject private var viewModel = CourseViewModel()

    var body: some View {
        NavigationView {
            List(viewModel.courses, id: \.id) { course in
                VStack(alignment: .leading, spacing: 8) {
                    Text(course.title)
                        .font(.headline)
                    Text(course.body)
                        .font(.subheadline)
                }
                .padding(.vertical, 4)
            }
            .navigationTitle("Courses")
            .onAppear {
                viewModel.fetchCourses()
            }
        }
    }
}

#Preview {
    CourseListView()
}

//
//  CourseView.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CourseView: View {
    @StateObject private var viewModel = CourseViewModel()

    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                UserView()
                    .accessibilityIdentifier("greeting_text")

                if viewModel.isLoading {
                    ProgressView("Loading courses...")
                        .progressViewStyle(CircularProgressViewStyle(tint: .blue))
                        .font(.headline)
                        .padding(.top, 40)
                } else if viewModel.courses.isEmpty {
                    VStack(spacing: 12) {
                        Image(systemName: "exclamationmark.circle")
                            .font(.largeTitle)
                            .foregroundColor(.gray)
                        Text("No courses available.")
                            .font(.headline)
                            .foregroundColor(.gray)
                    }
                    .padding(.top, 40)
                } else {
                    ForEach(viewModel.courses, id: \.id) { course in
                        CourseCardView(course: course) {
                            getImageForCourse(course)
                        }
                    }
                }
            }
            .padding()
        }
        .onAppear {
            viewModel.fetchCourses()
        }
    }

    func getImageForCourse(_ course: Course) -> Image {
        switch course.id {
        case "c6ae129f-e890-4ac8-964f-48055a78430c":
            return Image("bg_1")
        case "b53cfbef-507f-4261-aa96-90873398e558":
            return Image("bg_2")
        case "fe42e9c1-f205-4fa2-85f5-2346c06c8e34":
            return Image("bg_3")
        case "5ba2d12e-d5c2-4d61-95fd-6d6e89c1ad63":
            return Image("bg_4")
        default:
            return Image("bg_0")
        }
    }
}

#Preview {
    CourseView()
}

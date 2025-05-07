//
//  CourseListView.swift
//  iosApp
//
//  Created by Andriiana Konar on 14/04/2025.
//  Copyright © 2025 orgName. All rights reserved.

import SwiftUI
import shared

struct CourseListView: View {
    @StateObject private var viewModel = CourseViewModel()
    
    var body: some View {
        ScrollView {
            VStack(spacing: 16) {
                ForEach(viewModel.courses, id: \.id) { course in
                    CourseCardView(course: course) {
                        getImageForCourse(course)
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
            return Image("cpp_background")
        case "12345678-aaaa-bbbb-cccc-987654321000":
            return Image("python_background")
        default:
            return Image("default_course_background")
        }
    }

}


#Preview {
    CourseListView()
}

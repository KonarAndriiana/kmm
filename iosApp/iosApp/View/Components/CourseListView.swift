//
//  CourseListView.swift
//  iosApp
//
//  Created by Andriiana Konar on 14/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
//import SwiftUI
//import shared
//
//struct CourseListView: View {
//    @StateObject private var viewModel = CourseViewModel()
//
//    var body: some View {
//        NavigationView {
//            ScrollView {
//                LazyVStack(spacing: 16) {
//                    ForEach(viewModel.courses, id: \.id) { course in
//                        CourseCard(course: course)
//                    }
//                }
//                .padding()
//            }
//            .navigationTitle("Courses")
//            .onAppear {
//                viewModel.fetchCourses()
//            }
//        }
//    }
//}
//
//struct CourseCard: View {
//    let course: Course
//
//    var body: some View {
//        VStack(alignment: .leading, spacing: 8) {
//            Text(course.name)
//                .font(.title3)
//                .fontWeight(.semibold)
//                .foregroundColor(.primary)
//
//            Text(course.body)
//                .font(.body)
//                .foregroundColor(.secondary)
//        }
//        .padding()
//        .background(Color(.systemBackground))
//        .cornerRadius(16)
//        .shadow(color: Color.black.opacity(0.1), radius: 6, x: 0, y: 4)
//    }
//}
//
//#Preview {
//    CourseListView()
//}

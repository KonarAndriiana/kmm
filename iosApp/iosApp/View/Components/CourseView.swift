//
//  CourseView.swift
//  iosApp
//
//  Created by Andriiana Konar on 13/05/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct CourseView: View {
    @StateObject private var viewModel = CourseViewModel()

    var body: some View {
        VStack(spacing: 20) {
            VStack(alignment: .leading, spacing: 10) {
                HStack {
                    Text("Course")
                        .font(.system(size: 30))
                        .fontWeight(.bold)

                    Spacer()

                    Button {
                        // code
                    } label: {
                        Text("see all")
                            .font(.system(size: 15))
                            .fontWeight(.light)
                            .foregroundStyle(.gray)
                            .underline()
                    }
                }

                Text("""
                     Ready to learn? Choose your course and start your journey! Select a topic that interests you and dive into a new adventure in coding
                     """)
                .font(.system(size: 20))
                .fontWeight(.regular)
            }
            .padding()

            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 16) {
                    ForEach(viewModel.courses.map { Courses(from: $0) }) { course in
                        CourseItem(courses: course)
                    }
                }
                .padding(.horizontal)
            }
        }
        .onAppear {
            viewModel.fetchCourses()
        }
    }
}


#Preview {
    CourseView()
}

//
//  CoursesListView.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/05/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CoursesListView: View {
    let courses: [Course]

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                Text("All Courses")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .padding(.horizontal)

                ForEach(courses, id: \.id) { course in
                    NavigationLink(destination: LecturesListView(courseId: course.id)) {
                        CourseCardView(
                            course: course,
                            photo: { courseImage(for: course.id) } // ✅ closure bez ()
                        )
                    }
                }
                .padding(.horizontal)
            }
            .padding(.top)
        }
        .navigationTitle("Courses")
        .navigationBarTitleDisplayMode(.inline)
    }

    // 👇 Rovnaká logika ako v CourseView
    func courseImage(for id: String) -> Image {
        let hashValue = abs(id.hashValue)
        let imageNumber = (hashValue % 4) + 1
        let imageName = "bg_\(imageNumber)"
        
        if UIImage(named: imageName) != nil {
            return Image(imageName)
        } else {
            return Image("bg_0")
        }
    }
}


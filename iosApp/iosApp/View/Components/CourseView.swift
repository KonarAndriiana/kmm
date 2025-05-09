//
//  CourseView.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/05/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CourseView: View {
    @StateObject private var viewModel = CourseViewModel()

    var body: some View {
        VStack {
            Text("Your Courses")
                .font(.largeTitle)
                .fontWeight(.bold)
                .padding(.top)
            
            ScrollView {
                VStack(spacing: 16) {
                    ForEach(viewModel.courses, id: \.id) { course in
                        CourseCardView(course: course, photo: courseImage(for: course.id)) // Priamy Image
                    }
                }
                .padding(.horizontal)
            }
        }
        .onAppear {
            viewModel.fetchCourses()
        }
    }

    // Funkcia pre dynamický výber obrázka podľa ID
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

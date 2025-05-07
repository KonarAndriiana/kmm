//
//  CourseCardView.swift
//  iosApp
//
//  Created by Andriiana Konar on 06/05/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CourseCardView: View {
    let course: Course
    let photo : () -> Image
    
    var body: some View {
        ZStack {
            photo() // pozadie
                .resizable()
                .scaledToFill()
                .clipped()
                .overlay(Color.black.opacity(0.3)) // voliteľne stmavenie pre čitateľnosť textu
                .cornerRadius(20)
            
            VStack(alignment: .leading, spacing: 12) {
                HStack {
                    Image(systemName: "book.fill")
                        .foregroundColor(.blue)
                        .imageScale(.large)
                    Text(course.name)
                        .font(.title2)
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                    Spacer()
                }

                Text(course.courseDescription)
                    .font(.body)
                    .foregroundColor(.white.opacity(0.9))
                    .lineLimit(3)

                HStack {
                    Label(course.level.capitalized, systemImage: "bolt.fill")
                        .font(.caption)
                        .padding(6)
                        .background(Color.white.opacity(0.2))
                        .foregroundColor(.white)
                        .cornerRadius(8)

                    Spacer()

                    Text(course.specification.uppercased())
                        .font(.caption2)
                        .fontWeight(.semibold)
                        .padding(.horizontal, 8)
                        .padding(.vertical, 4)
                        .background(Color.white.opacity(0.2))
                        .cornerRadius(8)
                }
            }
            .padding()
        }
        .frame(width: 350, height: 200)
        .clipShape(RoundedRectangle(cornerRadius: 20))
        .shadow(color: Color.black.opacity(0.2), radius: 8, x: 0, y: 4)
    }
}

#Preview {
    CourseCardView(
        course: Course(
            id: "c6ae129f-e890-4ac8-964f-48055a78430c",
            name: "C++ course",
            courseDescription: "Basic C++ course",
            level: "Beginner",
            specification: "Backend"
        ),
        photo: { Image("background_sample") } // použiješ svoj asset alebo systemName
    )
}


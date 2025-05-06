//
//  CourseCardView.swift
//  iosApp
//
//  Created by Andriiana Konar on 06/05/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CourseCardsView: View {
    let course: Course

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            // Názov kurzu s ikonou
            HStack {
                Image(systemName: "book.closed.fill")
                    .foregroundColor(.blue)
                    .imageScale(.large)
                Text(course.name)
                    .font(.title2)
                    .fontWeight(.bold)
                    .foregroundColor(.primary)
            }

            // Popis kurzu
            Text(course.courseDescription)
                .font(.body)
                .foregroundColor(.secondary)
                .lineLimit(3)

            // Úroveň a špecifikácia ako tagy
            HStack {
                Label(course.level.capitalized, systemImage: "bolt.fill")
                    .font(.caption)
                    .padding(6)
                    .background(Color.blue.opacity(0.1))
                    .foregroundColor(.blue)
                    .cornerRadius(8)

                Spacer()

                Text(course.specification.uppercased())
                    .font(.caption2)
                    .fontWeight(.semibold)
                    .padding(.horizontal, 8)
                    .padding(.vertical, 4)
                    .background(Color.gray.opacity(0.2))
                    .cornerRadius(8)
            }
        }
        .padding(20)
        .background(
            RoundedRectangle(cornerRadius: 20)
                .fill(Color(.systemBackground))
                .shadow(color: Color.black.opacity(0.05), radius: 8, x: 0, y: 4)
        )
        
    }
}

#Preview {
    CourseCardsView(course: Course(
        id: "c6ae129f-e890-4ac8-964f-48055a78430c",
        name: "C++ course",
        courseDescription: "Basic C++ course",
        level: "Beginner",
        specification: "Backend"
    ))
}

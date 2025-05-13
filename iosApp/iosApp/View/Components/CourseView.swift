//
//  CourseView.swift
//  iosApp
//
//  Created by Andriiana Konar on 13/05/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct Courses : Identifiable {
    let id = UUID()
    let name : String
    let specification : String
    let level : String
    let photo : String?
}

struct CourseView: View {
    
    var coursesList : [Courses] = [
        .init(name: "Java", specification: "Backend", level: "Beginner", photo: "bg_1"),
        .init(name: "SQL", specification: "Database", level: "Intermediate", photo: "bg_2"),
        .init(name: "IT security", specification: "Security", level: "Intermediate" , photo: "bg_3")
    ]
    
    var body: some View {
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 16) {
                    ForEach(coursesList) { course in
                        CourseItem(courses: course)
                    }
                }
                .padding(.horizontal)
            }
        }
}


#Preview {
    CourseView()
}

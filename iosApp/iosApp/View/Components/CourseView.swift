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
        .init(name: "SQL", specification: "Database", level: "Intermediate", photo: "bg_4"),
        .init(name: "IT security", specification: "Security", level: "Intermediate" , photo: "bg_0")
    ]
    
    var body: some View {
        
        VStack(spacing: 20) {
            
            VStack(alignment: .leading , spacing: 10) {
                HStack {
                    Text("Course")
                        .font(.system(size: 30))
                        .fontWeight(.bold)
                    
                    Spacer()
                    
                    Button{
                        //code
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
                    ForEach(coursesList) { course in
                        CourseItem(courses: course)
                    }
                }
                .padding(.horizontal)
            }
        }
        
    }
}


#Preview {
    CourseView()
}

//
//  CourseItem.swift
//  iosApp
//
//  Created by Andriiana Konar on 13/05/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct CourseItem: View {
    
    let courses : Courses
    
    var body: some View {
        HStack {
            VStack(alignment: .leading ,spacing: 30){
                HStack{
                    Text(courses.level)
                        .font(.subheadline)
                        .fontWeight(.light)
                        .foregroundColor(.white)
                    
                    Spacer()
                    
                    Button {
                        //code
                    } label: {
                        Image(systemName: "heart")
                            .foregroundStyle(Color.white)
                            .font(.system(size: 25))
                    }
                    .padding(.top, 10.0)
                    
                }
                
                VStack(alignment: .leading ) {
                    Text(courses.name)
                        .font(.system(size: 25))
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                    
                    Text(courses.specification)
                        .font(.system(size: 10))
                        .fontWeight(.bold)
                        .foregroundColor(.black)
                        .padding(.horizontal, 15.0)
                        .padding(.vertical, 5.0)
                        .background(
                            Capsule()
                                .fill(Color.white)
                        )

                }
            }
        }
        .padding()
        .frame(width: 300.0, height: 150.0)
        .background(Color.black)
        .cornerRadius(10)
    }
}

#Preview {
    CourseItem(courses: .init(name: "Java", specification: "Backend", level: "Beginner"))
}

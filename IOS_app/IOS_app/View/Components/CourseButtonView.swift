//
//  CourseButtonView.swift
//  IOS_app
//
//  Created by Andriiana Konar on 28/03/2025.
//
import SwiftUI

struct CourseButtonView: View {
    
    let title: String
    let subtitle: String
    let bg_color: Color
    
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 10)
                .foregroundColor(bg_color)
            
            VStack(alignment: .leading) {
                Text(title)
                    .font(.title)
                    .fontWeight(.medium)
                    .lineLimit(1)
                
                Text(subtitle)
                    .padding(.top, 2.0)
            }
            .padding()
        }
        .padding()
    }
}



#Preview {
    CourseButtonView(
        title: "Course",
        subtitle: "Ready to learn? Choose your course and start your journey! Select a topic that interests you and dive into a new adventure in coding.",
        bg_color: .gray
    )
}

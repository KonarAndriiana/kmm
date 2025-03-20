//
//  CourseView.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//

import SwiftUI

struct HomeView: View {
    var body: some View {
        VStack(alignment: .leading){
            HStack{
                Text("Name : ")
                Text("")
            }
            HStack{
                Text("Email : ")
                Text("")
            }
            HStack{
                Text("Member since : ")
                Text("")
            }
        }
        
        ButtonView(color: .red, title: "Log out") {
            //pridat log out tu (nezbudni state)
        }
               
    }
}

#Preview {
    HomeView()
}

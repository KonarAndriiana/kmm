//
//  CourseView.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//

import SwiftUI

struct HomeView: View {
    
    @StateObject var viewModel = HomeViewViewModel()
    
    var body: some View {
        NavigationView {
            VStack {
                if let user = viewModel.user {
                    VStack {
                        HStack {
                            Text("Name: ")
                            Text(user.name)
                        }
                        .padding()
                        
                        HStack {
                            Text("Email: ")
                            Text(user.email)
                        }
                        .padding()
                        
                        HStack {
                            Text("Member since: ")
                            Text("\(Date(timeIntervalSince1970: user.joined).formatted(date: .abbreviated, time: .shortened))")
                        }
                        .padding()
                        
                        ButtonView(color: .red, title: "Log Out") {
                            viewModel.logout()
                        }
                    }
                } else {
                    Text("Loading ...")
                }
            }
        }
        .onAppear {
            viewModel.fetchUser()
        }
    }
}

#Preview {
    HomeView()
}

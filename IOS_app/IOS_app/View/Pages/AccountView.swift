//
//  CourseView.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//

import SwiftUI

struct AccountView: View {
    
    @StateObject var viewModel = AccountViewViewModel()
    
    var body: some View {
        NavigationView {
            VStack {
                if let user = viewModel.user {
                    VStack {
                        HStack {
                            Text("First name: ")
                            Text(user.firstName)
                        }
                        .padding()
                        
                        HStack {
                            Text("Last name: ")
                            Text(user.lastName)
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
    AccountView()
}

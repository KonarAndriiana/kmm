//
//  UserView.swift
//  IOS_app
//
//  Created by Andriiana Konar on 31/03/2025.
//

import SwiftUI

struct UserView: View {

    @StateObject var viewModel = AccountViewViewModel()
    
    var body: some View {
        NavigationView {
            VStack {
                if let user = viewModel.user {
                    HStack {
                        Text("Hi, \(user.name) 👋🏻")
                            .font(.largeTitle)
                            .fontWeight(.semibold)
                            .multilineTextAlignment(.leading)
                        
                        Spacer()
                        
                        NavigationLink(destination: SettingsView()) {
                            Image(systemName: "person.crop.circle")
                                .resizable()
                                .scaledToFit()
                                .frame(width: 60, height: 60)
                                .foregroundColor(.gray)
                        }
                    }
                    .padding(.all, 20)
                } else {
                    HStack {
                        Text("Hi 👋🏻")
                            .font(.largeTitle)
                            .fontWeight(.semibold)
                            .multilineTextAlignment(.leading)
                        
                        Spacer()
                        
                        NavigationLink(destination: SettingsView()) {
                            Image(systemName: "person.crop.circle")
                                .resizable()
                                .scaledToFit()
                                .frame(width: 60, height: 60)
                                .foregroundColor(.gray)
                        }
                    }
                    .padding(.all, 20)
                }
                
                Spacer()
            }
            .onAppear {
                viewModel.fetchUser()
            }
        }
    }
}

#Preview {
    NavigationStack {
        UserView()
    }
}




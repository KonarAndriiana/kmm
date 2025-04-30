//
//  UserView.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct UserView: View {

    @StateObject var viewModel = AccountViewViewModel()
    
    var body: some View {
        NavigationView {
            VStack {
                if let user = viewModel.user {
                    HStack {
                        Text("Hi, \(user.firstName) 👋🏻")
                            .font(.largeTitle)
                            .fontWeight(.semibold)
                            .multilineTextAlignment(.leading)
                        
                        Spacer()
                        
                        NavigationLink(destination: AccountView()) {
                            Image(systemName: "person.crop.circle")
                                .resizable()
                                .scaledToFit()
                                .frame(width: 60, height: 60)
                                .foregroundColor(.gray)
                        }
                        .accessibilityIdentifier("user_icon")
                    }
                    .padding(.all, 20)
                } else {
                    HStack {
                        Text("Hi 👋🏻")
                            .font(.largeTitle)
                            .fontWeight(.semibold)
                            .multilineTextAlignment(.leading)
                        
                        Spacer()
                        
                        NavigationLink(destination: AccountView()) {
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


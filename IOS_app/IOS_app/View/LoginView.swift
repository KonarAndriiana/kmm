//
//  LoginView.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//

import SwiftUI

struct LoginView: View {
    
    @StateObject var viewModel = LoginViewViewModel()
    
    var body: some View {
        NavigationView {
            VStack {
                
            
                
                Form {
                    
                    TextField("Email" , text: $viewModel.email)
                        .autocapitalization(.none)
                        .autocorrectionDisabled()
                    
                    SecureField("Password" , text: $viewModel.password)
                    
                    ButtonView(color: .red, title: "Log In") {
                        viewModel.login()
                    }
                    
                    if !viewModel.errorMessage.isEmpty {
                        Text(viewModel.errorMessage)
                            .foregroundColor(Color.orange)
                            .multilineTextAlignment(.center)
                    }
                }
                .padding(.top, 200)
                
                VStack {
                    Text("New around here?")
                    NavigationLink("Create an account" , destination: RegistrationView())
                }
                .padding(.bottom , 250)
            }
        }
    }
}

#Preview {
    LoginView()
}

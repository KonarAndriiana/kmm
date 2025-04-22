//
//  LogInBttnView.swift
//  iosApp
//
//  Created by Andriiana Konar on 22/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct LogInBttnView: View {
    @StateObject var viewModel = LoginViewViewModel()
    @State private var showForgotPasswordSheet = false
    
    var body: some View {
        VStack(spacing: 70){
            
            Button("Forgot Password?") {
                showForgotPasswordSheet = true
            }
            .foregroundColor(.white)
            .padding(.top, 30)
            .accessibilityIdentifier("forgot_btn")
            
            ButtonView(title: "Log In") {
                viewModel.login()
            }
            .frame(width: 150.0, height: 60.0)
            .accessibilityIdentifier("login_btn")
            
            HStack {
                Text("Don't have an account?")
                    .font(.system(size: 15))
                    .foregroundColor(Color.white)
                    .accessibilityIdentifier("sign_up_text")
                    
                
                NavigationLink(destination: RegistrationView()) {
                    Text("Sign up now")
                        .font(.system(size: 15))
                        .foregroundColor(.white)
                        .underline()
                        .bold()
                }
                .accessibilityIdentifier("sign_up_redirect")
            }
        }
    }
}

#Preview {
    LogInBttnView()
}

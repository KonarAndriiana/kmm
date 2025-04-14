//
//  LoginView.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct LoginView: View {
    
    @StateObject var viewModel = LoginViewViewModel()
    @State private var showForgotPasswordSheet = false
    @State private var showToast = false
    
    var body: some View {
        NavigationView {
            VStack {
                VStack {
                    Text("Welcome")
                        .accessibilityIdentifier("welcome_text")
                    
                    HStack{
                        Text("back to")
                        Text("Bugless")
                    }
                    .accessibilityIdentifier("bugless_text")
                }
                .padding(.top, 100)
                
                Form {
                    TextFieldView(placeholder: "Email", text: $viewModel.email)
                        .autocapitalization(.none)
                        .autocorrectionDisabled()
                        .accessibilityIdentifier("email_input")
                    
                    TextFieldView(placeholder: "Password", text: $viewModel.password, isSecure: true)
                        .autocapitalization(.none)
                        .autocorrectionDisabled()
                        .accessibilityIdentifier("password_input")
                    
                    ButtonView(color: .red, title: "Log In") {
                        viewModel.login()
                    }
                    .accessibilityIdentifier("login_btn")
                    
                    Button("Forgot Password?") {
                        showForgotPasswordSheet = true
                    }
                    .foregroundColor(.blue)
                    .padding(.top, 10)
                    .accessibilityIdentifier("forgot_btn")
                }
                .padding(.top, 100)
                
                VStack {
                    Text("New around here?")
                        .accessibilityIdentifier("sign_up_text")
                    NavigationLink("Create an account", destination: RegistrationView())
                        .accessibilityIdentifier("sign_up_redirect")
                }
                .padding(.bottom, 250)
            }
            .overlay(
                VStack {
                    if showToast {
                        HStack {
                            Text(viewModel.errorMessage)
                                .font(.body)
                                .foregroundColor(.white)
                                .padding()
                                .frame(maxWidth: .infinity)
                                .background(Color.gray)
                                .cornerRadius(10)
                                .shadow(radius: 10)
                        }
                        .padding(.horizontal)
                        .frame(maxWidth: 400)
                        .transition(.opacity)
                        .zIndex(1)
                        .onAppear {
                            DispatchQueue.main.asyncAfter(deadline: .now() + 5) {
                                withAnimation {
                                    showToast = false
                                }
                            }
                        }
                    }
                    Spacer()
                }
                    .accessibilityIdentifier("error_msg")
            )
            .sheet(isPresented: $showForgotPasswordSheet) {
                ForgotPasswordView(viewModel: viewModel)
            }
        }
        .onChange(of: viewModel.errorMessage) { newError in
            if !newError.isEmpty {
                showToast = true
            }
        }
    }
}

#Preview {
    LoginView()
}

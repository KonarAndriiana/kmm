//
//  RegistrationView.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct RegistrationView: View {
    
    @StateObject var viewModel = RegistrationViewViewModel()
    @State private var showToast = false
    
    var body: some View {
        NavigationView{
            ZStack{
                Image("backgroundImage")
                    .resizable()
                    .scaledToFill()
                    .ignoresSafeArea()
                    .accessibilityIdentifier("background_image")
                
                VStack(spacing: 150){
                    Text("Create account")
                        .font(.system(size: 35))
                        .multilineTextAlignment(.center)
                        .foregroundColor(Color.white)
                        .bold()
                        .accessibilityIdentifier("create_acc_text")
                    
                    VStack(spacing: 50) {
                        TextFieldView(placeholder: "first name", text: $viewModel.firstName)
                            .frame(width: 440.0, height: 20.0)
                            .autocorrectionDisabled()
                            .accessibilityIdentifier("first_name_input")
                        
                        TextFieldView(placeholder: "last name", text: $viewModel.lastName)
                            .frame(width: 440.0, height: 20.0)
                            .autocorrectionDisabled()
                            .accessibilityIdentifier("last_name_input")
                        
                        TextFieldView(placeholder: "email", text: $viewModel.email)
                            .frame(width: 440.0, height: 20.0)
                            .autocapitalization(.none)
                            .autocorrectionDisabled()
                            .accessibilityIdentifier("email_input")
                        
                        TextFieldView(placeholder: "password", text: $viewModel.password, isSecure: true)
                            .frame(width: 440.0, height: 20.0)
                            .autocapitalization(.none)
                            .autocorrectionDisabled()
                            .accessibilityIdentifier("password_input")
                        
                        TextFieldView(placeholder: "confirm password", text: $viewModel.confirmPassword, isSecure: true)
                            .frame(width: 440.0, height: 20.0)
                            .autocapitalization(.none)
                            .autocorrectionDisabled()
                            .accessibilityIdentifier("confirm_password")
                    }
                    
                    VStack(spacing: 30) {
                        ButtonView(title: "Sign Up") {
                            viewModel.registration()
                        }
                        .disabled(viewModel.password != viewModel.confirmPassword || viewModel.password.isEmpty || viewModel.confirmPassword.isEmpty)
                        .opacity((viewModel.password != viewModel.confirmPassword || viewModel.password.isEmpty || viewModel.confirmPassword.isEmpty) ? 0.5 : 1.0)
                        .frame(width: 150.0, height: 60.0)
                        .accessibilityIdentifier("sign_up_btn")
                        
                        HStack {
                            Text("Already have an account?")
                                .font(.system(size: 15))
                                .foregroundColor(Color.white)
                                .accessibilityIdentifier("sign_up_text")
                                
                            
                            NavigationLink(destination: LoginView()) {
                                Text("Log In")
                                    .font(.system(size: 15))
                                    .foregroundColor(.white)
                                    .underline()
                                    .bold()
                            }
                            .accessibilityIdentifier("log_in_redirect")
                        }
                    }
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
                .onChange(of: viewModel.errorMessage) { newError in
                    if !newError.isEmpty {
                        showToast = true
                    }
                }
            }
        }
    }
}

#Preview {
    RegistrationView()
}

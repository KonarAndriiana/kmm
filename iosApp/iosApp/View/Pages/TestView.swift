//
//  TestView.swift
//  iosApp
//
//  Created by Andriiana Konar on 15/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct TestView: View {
    
    @StateObject var viewModel = LoginViewViewModel()
    @State private var showForgotPasswordSheet = false
    @State private var showToast = false
    
    var body: some View {
        NavigationView {
            VStack {
                ZStack{
                    Image("backgroundImage")
                        .resizable()
                        .scaledToFill()
                        .ignoresSafeArea()
                        .accessibilityIdentifier("background_image")
                    
                    VStack {
                        VStack(spacing: 20) {
                            Text("Welcome")
                                .font(.system(size: 35))
                                .multilineTextAlignment(.center)
                                .foregroundColor(Color.white)
                                .bold()
                                .accessibilityIdentifier("welcome_text")
                            
                            HStack{
                                Text("back to")
                                    .foregroundColor(Color.white)
                                    .font(.system(size: 25))
                                
                                Text("Bugless")
                                    .font(.system(size: 25))
                                    .foregroundColor(Color.white)
                                    .bold()
                            }
                            .accessibilityIdentifier("bugless_text")
                        }
                        .padding(.bottom, 150)
                        
                        
                        //fields
                        VStack(spacing: 50) {
                            TextFieldView(placeholder: "email", text: $viewModel.email)
                                .frame(width: 440.0, height: 40.0)
                                .autocapitalization(.none)
                                .autocorrectionDisabled()
                                .accessibilityIdentifier("email_input")
                            
                            TextFieldView(placeholder: "password", text: $viewModel.password, isSecure: true)
                                .frame(width: 440.0, height: 40.0)
                                .autocapitalization(.none)
                                .autocorrectionDisabled()
                                .accessibilityIdentifier("password_input")
                        }
                        .padding(.top, 40)
                        
                        
                        
                        //log in
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
                        .padding(.bottom, 30)
                        
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
    TestView()
}

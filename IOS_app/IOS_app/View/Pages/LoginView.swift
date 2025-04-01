//
//  LoginView.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//

import SwiftUI

struct LoginView: View {
    
    @StateObject var viewModel = LoginViewViewModel()
    @State private var showForgotPasswordSheet = false
    @State private var showToast = false
    
    var body: some View {
        NavigationView {
            VStack {
                // Main form content
                Form {
                    TextField("Email" , text: $viewModel.email)
                        .autocapitalization(.none)
                        .autocorrectionDisabled()
                    
                    SecureField("Password" , text: $viewModel.password)
                    
                    ButtonView(color: .red, title: "Log In") {
                        viewModel.login()
                    }
                    
                    // Forgot password button
                    Button("Forgot Password?") {
                        showForgotPasswordSheet = true
                    }
                    .foregroundColor(.blue)
                    .padding(.top, 10)
                }
                .padding(.top, 200)
                
                VStack {
                    Text("New around here?")
                    NavigationLink("Create an account", destination: RegistrationView())
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
                        .frame(maxWidth: 400) // Limit the max width
                        .transition(.opacity) // Fade effect
                        .zIndex(1)
                        .onAppear {
                            // Keep the message visible for 5 seconds
                            DispatchQueue.main.asyncAfter(deadline: .now() + 5) {
                                withAnimation {
                                    showToast = false
                                }
                            }
                        }
                    }
                    Spacer()
                }
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

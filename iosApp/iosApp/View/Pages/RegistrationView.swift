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
        VStack {
            VStack{
                Text("Create Account")
                    .accessibilityIdentifier("create_acc_text")
            }
            .padding(.top, 100)
            
            Form {
                TextFieldView(placeholder: "First name", text: $viewModel.firstName)
                    .autocorrectionDisabled()
                    .accessibilityIdentifier("first_name_input")
                
                TextFieldView(placeholder: "Last name", text: $viewModel.lastName)
                    .autocorrectionDisabled()
                    .accessibilityIdentifier("last_name_input")
                
                TextFieldView(placeholder: "Email", text: $viewModel.email)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                    .accessibilityIdentifier("email_input")
                
                TextFieldView(placeholder: "Password", text: $viewModel.password, isSecure: true)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                    .accessibilityIdentifier("password_input")
                
                TextFieldView(placeholder: "Confirm Password", text: $viewModel.confirmPassword, isSecure: true)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                    .accessibilityIdentifier("confirm_password")
                
                ButtonView(color: .blue, title: "Sign Up") {
                    viewModel.registration()
                }
                .disabled(viewModel.password != viewModel.confirmPassword || viewModel.password.isEmpty || viewModel.confirmPassword.isEmpty)
                .opacity((viewModel.password != viewModel.confirmPassword || viewModel.password.isEmpty || viewModel.confirmPassword.isEmpty) ? 0.5 : 1.0)
                .accessibilityIdentifier("sign_up_btn")
            }
            .padding(.top, 100)
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

#Preview {
    RegistrationView()
}

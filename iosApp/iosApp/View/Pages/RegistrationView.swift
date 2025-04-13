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
            Form {
                TextFieldView(placeholder: "First name", text: $viewModel.firstName)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                
                TextFieldView(placeholder: "Last name", text: $viewModel.lastName)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                
                TextFieldView(placeholder: "Email", text: $viewModel.email)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                
                TextFieldView(placeholder: "Password", text: $viewModel.password, isSecure: true)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                
                TextFieldView(placeholder: "Confirm Password", text: $viewModel.confirmPassword, isSecure: true)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                
                ButtonView(color: .blue, title: "Create account") {
                    viewModel.registration()
                }
                .disabled(viewModel.password != viewModel.confirmPassword || viewModel.password.isEmpty || viewModel.confirmPassword.isEmpty)
                .opacity((viewModel.password != viewModel.confirmPassword || viewModel.password.isEmpty || viewModel.confirmPassword.isEmpty) ? 0.5 : 1.0)
            }
            .padding(.top, 200)
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

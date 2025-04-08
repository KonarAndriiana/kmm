//
//  LoginView1.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct LoginView1: View {

    @StateObject var viewModel = LoginViewViewModel()
    @State private var showForgotPasswordSheet = false
    @State private var showToast = false
    
    var body: some View {
        NavigationView {
            ZStack {
                Image("background")
                    .resizable()
                    .scaledToFill()
                    .ignoresSafeArea()

                VStack {
                    TextFieldView(placeholder: "Email", text: $viewModel.email)
                        .frame(width: 400.0, height: 100.0)
                        .autocapitalization(.none)
                        .autocorrectionDisabled()

                    TextFieldView(placeholder: "Password", text: $viewModel.password, isSecure: true)
                        .frame(width: 400.0, height: 100.0)
                        .autocapitalization(.none)
                        .autocorrectionDisabled()

                    ButtonView(color: .red, title: "Log In") {
                        viewModel.login()
                    }
                    .frame(width: 100.0, height: 50.0)

                    Button("Forgot Password?") {
                        showForgotPasswordSheet = true
                    }
                    .foregroundColor(Color.white)
                    .padding(.all, 50)

                    VStack {
                        Text("New around here?")
                        NavigationLink("Create an account", destination: RegistrationView())
                            .foregroundColor(Color.white)
                    }
                    .padding(.bottom, 250)
                }
                .padding()
                .background(Color.white.opacity(0))
                .cornerRadius(20)
                .padding()

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

}

#Preview {
    LoginView1()
}

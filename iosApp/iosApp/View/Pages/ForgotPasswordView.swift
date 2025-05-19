//
//  ForgotPasswordView.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct ForgotPasswordView: View {
    @ObservedObject var viewModel: LoginViewViewModel
    @State private var email = ""
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        VStack {
            Text("Forgot Password")
                .font(.title)
                .fontWeight(.bold)
                .padding(.bottom, 20)
                .accessibilityIdentifier("forgot_pass_text")
            
            Text("Enter your email, and we’ll send you link and instructions to reset your password.")
                .accessibilityIdentifier("info_text")
                .multilineTextAlignment(.center)
            
            TextFieldView(placeholder: "Enter your email", text: $email)
                .autocapitalization(.none)
                .autocorrectionDisabled()
                .multilineTextAlignment(.center)
                .padding()
                .accessibilityIdentifier("email_input")
            
            Button(action: {
                viewModel.resetPassword(email: email)
                presentationMode.wrappedValue.dismiss()
            }) {
                Text("Reset Password")
                    .foregroundColor(.white)
                    .padding()
                    .frame(width: 200 , height:  40)
                    .background(Color.black)
                    .cornerRadius(10)
            }
            .padding()
            .accessibilityIdentifier("reset_btn")
            
            if !viewModel.errorMessage.isEmpty {
                Text(viewModel.errorMessage)
                    .foregroundColor(.orange)
                    .multilineTextAlignment(.center)
                    .padding()
            }
        }
        .padding()
    }
}

#Preview {
    ForgotPasswordView(viewModel: LoginViewViewModel())
}

//
//  ForgotPasswordView.swift
//  IOS_app
//
//  Created by Andriiana Konar on 01/04/2025.
//
import SwiftUI

struct ForgotPasswordView: View {
    @ObservedObject var viewModel: LoginViewViewModel
    @State private var email = ""
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        VStack {
            Text("Reset Password")
                .font(.title)
                .fontWeight(.bold)
                .padding(.bottom, 20)
            
            TextField("Enter your email", text: $email)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .autocapitalization(.none)
                .autocorrectionDisabled()
                .padding()
            
            Button(action: {
                viewModel.resetPassword(email: email)
                presentationMode.wrappedValue.dismiss()
            }) {
                Text("Reset Password")
                    .foregroundColor(.white)
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color.blue)
                    .cornerRadius(10)
            }
            .padding()
            
            if !viewModel.errorMessage.isEmpty {
                Text(viewModel.errorMessage)
                    .foregroundColor(.white)
                    .multilineTextAlignment(.center)
                    .padding()
            }
        }
        .padding()
    }
}

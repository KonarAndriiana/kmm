//
//  RegistrationView.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//
import SwiftUI

struct RegistrationView: View {
    
    @StateObject var viewModel = RegistrationViewViewModel()
    
    var body: some View {
        VStack {
            Form {
                
                //ImagePickerView()
                
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
                
                TextFieldView(placeholder: "Confirm Password", text: $viewModel.confirmPassword, isSecure: true)
                
                ButtonView(color: .blue, title: "Create account") {
                    viewModel.registration()
                }
                
                if !viewModel.errorMessage.isEmpty {
                    Text(viewModel.errorMessage)
                        .foregroundColor(.orange)
                        .multilineTextAlignment(.center)
                }
            }
            .padding(.top, 200)
        }
    }
}

#Preview {
    RegistrationView()
}

//
//  RegistrationInputView.swift
//  iosApp
//
//  Created by Andriiana Konar on 22/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct RegistrationInputView: View {
    
    @StateObject var viewModel = RegistrationViewViewModel()
    
    var body: some View {
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
    }
}

#Preview {
    RegistrationInputView()
}

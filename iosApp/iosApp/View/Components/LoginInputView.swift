//
//  LoginInputView.swift
//  iosApp
//
//  Created by Andriiana Konar on 22/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct LoginInputView: View {
    
    @StateObject var viewModel = LoginViewViewModel()
    @State private var showForgotPasswordSheet = false
    
    var body: some View {
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
    }
}

#Preview {
    LoginInputView()
}

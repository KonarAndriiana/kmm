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
                TextField("Name" , text: $viewModel.name)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                
                TextField("Email" , text: $viewModel.email)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                
                SecureField("Password" , text: $viewModel.password)
                
                ButtonView(color: .blue, title: "Create account") {
                    viewModel.registration()
                }
                
                if !viewModel.errorMessage.isEmpty {
                    Text(viewModel.errorMessage)
                        .foregroundColor(Color.orange)
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

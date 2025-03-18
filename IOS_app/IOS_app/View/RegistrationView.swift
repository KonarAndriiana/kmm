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
                TextField("Email" , text: $viewModel.email)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                
                SecureField("Password" , text: $viewModel.password)
                
                ButtonView(color: .blue, title: "Create account") {
                    //registration
                }
            }
            .padding(.top, 200)
        }
    }
}

#Preview {
    RegistrationView()
}

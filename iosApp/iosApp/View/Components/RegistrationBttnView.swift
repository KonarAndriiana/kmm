//
//  RegistrationBttnView.swift
//  iosApp
//
//  Created by Andriiana Konar on 22/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct RegistrationBttnView: View {
    
    @StateObject var viewModel = RegistrationViewViewModel()
    @State private var showToast = false
    
    var body: some View {
        VStack(spacing: 30) {
            ButtonView(title: "Sign Up") {
                viewModel.registration()
            }
            .disabled(viewModel.password != viewModel.confirmPassword || viewModel.password.isEmpty || viewModel.confirmPassword.isEmpty)
            .opacity((viewModel.password != viewModel.confirmPassword || viewModel.password.isEmpty || viewModel.confirmPassword.isEmpty) ? 0.5 : 1.0)
            .frame(width: 150.0, height: 60.0)
            .accessibilityIdentifier("sign_up_btn")
            
            HStack {
                Text("Already have an account?")
                    .font(.system(size: 15))
                    .foregroundColor(Color.white)
                    .accessibilityIdentifier("sign_up_text")
                    
                
                NavigationLink(destination: LoginView()) {
                    Text("Log In")
                        .font(.system(size: 15))
                        .foregroundColor(.white)
                        .underline()
                        .bold()
                }
                .accessibilityIdentifier("log_in_redirect")
            }
        }
    }
}

#Preview {
    RegistrationBttnView()
}

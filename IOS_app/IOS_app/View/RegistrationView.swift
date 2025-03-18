//
//  RegistrationView.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//

import SwiftUI

struct RegistrationView: View {
    
    @State var email = ""
    @State var password = ""
    
    var body: some View {
        VStack {
            Form {
                TextField("Email" , text: $email)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                
                SecureField("Password" , text: $password)
                
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

//
//  LoginViewViewModel.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import FirebaseAuth
import Foundation

class LoginViewViewModel: ObservableObject {
    @Published var email = ""
    @Published var password = ""
    @Published var errorMessage = ""
    
    init() {
        
    }
    
    func login() {
        guard validate() else {
            return
        }
        //will try to log user in
        Auth.auth().signIn(withEmail: email, password: password)
    }
    
    func resetPassword(email: String) {
        guard !email.trimmingCharacters(in: .whitespaces).isEmpty else {
            errorMessage = "Enter your email to continue."
            return
        }

        Auth.auth().sendPasswordReset(withEmail: email) { error in
            if let error = error {
                self.errorMessage = "Something went wrong. Try again."
            } else {
                self.errorMessage = "Check your inbox for a reset link."
            }
        }
    }
    
    
    
    private func validate() -> Bool {
        guard !email.trimmingCharacters(in: .whitespaces).isEmpty,
              !password.trimmingCharacters(in: .whitespaces).isEmpty else {
            errorMessage = "Please fill in all fields"
            return false
        }
        
        guard email.contains("@")  && email.contains(".") else {
            errorMessage = "Please enter valid email."
            return false
        }
        
        
        // just for console log
        print("Trying to log in")
        
        return true
    }
}

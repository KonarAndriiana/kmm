//
//  LoginViewViewModel.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
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

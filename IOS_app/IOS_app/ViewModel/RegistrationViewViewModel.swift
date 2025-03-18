//
//  RegistrationViewViewModel.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//

import Foundation

class RegistrationViewViewModel: ObservableObject {
    @Published var email = " "
    @Published var password = " "
    
    init() {
        
    }
    
    func registration() {
        guard validate() else {
            return
        }
    }
    
    
    private func validate() -> Bool {
        guard !email.trimmingCharacters(in: .whitespaces).isEmpty,
              !password.trimmingCharacters(in: .whitespaces).isEmpty else {
            return false
        }
        
        guard email.contains("@")  && email.contains(".") else {
            return false
        }
        
        guard email.count >= 6 else {
            return false
        }
        
        // just for console log
        print("Trying to registrate")
        
        return true
    }
}

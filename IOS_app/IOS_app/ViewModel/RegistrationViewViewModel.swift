//
//  RegistrationViewViewModel.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//

import FirebaseFirestore
import FirebaseAuth
import Foundation

class RegistrationViewViewModel: ObservableObject {
    @Published var name = ""
    @Published var email = ""
    @Published var password = ""
    @Published var errorMessage = ""
    
    init() {
        
    }
    
    func registration() {
        guard validate() else {
            return
        }
        
        Auth.auth().createUser(withEmail: email, password: password) { [weak self] result, error in
            guard let userID = result?.user.uid else {
                return
            }
            self?.insertUserRrecord(id: userID)
        }
    }
    
    private func insertUserRrecord(id: String) {
        let newUser = User(id: id, name: name, email: email, joined: Date().timeIntervalSince1970)
        
        let db = Firestore.firestore()
        
        db.collection("users")
            .document(id)
            .setData(newUser.asDictionary())
    }
    
    
    
    private func validate() -> Bool {
        guard !name.trimmingCharacters(in: .whitespaces).isEmpty,
              !email.trimmingCharacters(in: .whitespaces).isEmpty,
              !password.trimmingCharacters(in: .whitespaces).isEmpty else {
            
            errorMessage = "Please fill in all fields"
            return false
        }
        
        guard email.contains("@")  && email.contains(".") else {
            errorMessage = "Please enter valid email."
            return false
        }
        
        guard email.count >= 6 else {
            errorMessage = "Please create password that has more that 5 Characters"
            return false
        }
        
        // just for console log
        print("Trying to registrate")
        
        return true
    }
}

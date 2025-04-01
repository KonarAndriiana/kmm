//
//  RegistrationViewViewModel.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//
import FirebaseAuth
import FirebaseFirestore
import Foundation

class RegistrationViewViewModel: ObservableObject {
    @Published var firstName = ""
    @Published var lastName = ""
    @Published var email = ""
    @Published var password = ""
    @Published var confirmPassword = ""  
    @Published var errorMessage = ""
    
    init() {}
        
    func registration() {
        guard validate() else {
            return
        }
        
        Auth.auth().createUser(withEmail: email, password: password) { [weak self] result, error in
            guard let userID = result?.user.uid else {
                return
            }
            self?.insertUserRecord(id: userID)
        }
    }
    
    private func insertUserRecord(id: String) {
        let newUser = User(id: id, firstName: firstName, lastName: lastName, email: email, joined: Date().timeIntervalSince1970)
        
        let db = Firestore.firestore()
        
        db.collection("users")
            .document(id)
            .setData(newUser.asDictionary())
    }
    
    private func validate() -> Bool {
        guard !firstName.trimmingCharacters(in: .whitespaces).isEmpty,
              !lastName.trimmingCharacters(in: .whitespaces).isEmpty,
              !email.trimmingCharacters(in: .whitespaces).isEmpty,
              !password.trimmingCharacters(in: .whitespaces).isEmpty,
              !confirmPassword.trimmingCharacters(in: .whitespaces).isEmpty else {
            
            errorMessage = "Please fill in all fields"
            return false
        }
        
        guard email.contains("@") && email.contains(".") else {
            errorMessage = "Please enter a valid email."
            return false
        }
        
        guard password.count >= 6 else {
            errorMessage = "Please create a password that has at least 6 characters."
            return false
        }
        
        guard password == confirmPassword else {
            errorMessage = "Passwords do not match."
            return false
        }
        
        return true
    }
}




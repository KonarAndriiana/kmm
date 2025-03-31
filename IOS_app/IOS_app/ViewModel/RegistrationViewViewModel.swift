//
//  RegistrationViewViewModel.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//

import SwiftUI
import FirebaseAuth
import Foundation
import FirebaseFirestore

class RegistrationViewViewModel: ObservableObject {
    @Published var name = ""
    @Published var email = ""
    @Published var password = ""
    @Published var errorMessage = ""
    @Published var profilePhoto: String?
    
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
            
            self?.saveProfilePhoto() 
            self?.insertUserRecord(id: userID)
        }
    }
    
    private func insertUserRecord(id: String) {
        let newUser = User(id: id, name: name, email: email, joined: Date().timeIntervalSince1970, profilePhoto: profilePhoto)
        
        UserDefaults.standard.set(profilePhoto, forKey: "profilePhoto")
        
        // Vytvorenie používateľského záznamu vo Firestore (ak budeš chcieť, môžeš túto časť upravit pre Firebase)
         let db = Firestore.firestore()
         db.collection("users").document(id).setData(newUser.asDictionary())
    }
    
    private func saveProfilePhoto() {
        if let photo = profilePhoto {
            UserDefaults.standard.set(photo, forKey: "profilePhoto")
        }
    }
    
    private func validate() -> Bool {
        guard !name.trimmingCharacters(in: .whitespaces).isEmpty,
              !email.trimmingCharacters(in: .whitespaces).isEmpty,
              !password.trimmingCharacters(in: .whitespaces).isEmpty else {
            
            errorMessage = "Please fill in all fields"
            return false
        }
        
        guard email.contains("@") && email.contains(".") else {
            errorMessage = "Please enter valid email."
            return false
        }
        
        guard password.count >= 6 else {
            errorMessage = "Please create a password with more than 5 characters"
            return false
        }
        
        // just for console log
        print("Trying to register")
        
        return true
    }
}

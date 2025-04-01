//
//  HomeViewViewModel.swift
//  IOS_app
//
//  Created by Andriiana Konar on 21/03/2025.
//
import FirebaseAuth
import FirebaseFirestore
import Foundation

class AccountViewViewModel: ObservableObject {
    @Published var user: User? = nil
    
    init() {}
    
    func fetchUser() {
        guard let userID = Auth.auth().currentUser?.uid else {
            return
        }
        
        let db = Firestore.firestore()
        db.collection("users").document(userID).getDocument { [weak self] snapshot, error in
            guard let data = snapshot?.data(), error == nil else {
                return
            }
            
            DispatchQueue.main.async {
                self?.user = User(id: data["id"] as? String ?? "",
                                  firstName: data["firstName"] as? String ?? "",
                                  lastName: data["lastName"] as? String ?? "",
                                  email: data["email"] as? String ?? "",
                                  joined: data["joined"] as? TimeInterval ?? 0)
            }
        }
    }
    
    func logout() {
        do {
            try Auth.auth().signOut()
        } catch {
            print(error)
        }
    }
}

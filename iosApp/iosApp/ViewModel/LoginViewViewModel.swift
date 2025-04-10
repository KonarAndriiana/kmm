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
    
    init() { }
    
    func login() {
//        print("Blyat")
//        self.sendLoginRequest(email: self.email, password: self.password)
//        print("Cyka")
        
        guard validate() else {
            return
        }
        
        // Perform Firebase sign-in first, then send the credentials to your backend
        Auth.auth().signIn(withEmail: email, password: password) { [weak self] authResult, error in
            if let error = error {
                self?.errorMessage = "Firebase sign-in failed: \(error.localizedDescription)"
                return
            }
            
            // Send the credentials to the backend for additional login logic (if needed)
            self?.sendLoginRequest(email: self?.email ?? "", password: self?.password ?? "")
        }
    }
    
    private func sendLoginRequest(email: String, password: String) {
        // Your backend API URL for login
        guard let url = URL(string: "http://192.168.10.101:5000") else {
            errorMessage = "Invalid URL"
            return
        }
        
        // Prepare the HTTP request body
//        let body: [String: String] = [
//            "email": email,
//            "password": password
//        ]
        
//        guard let jsonData = try? JSONSerialization.data(withJSONObject: body) else {
//            errorMessage = "Failed to create request body"
//            return
//        }
        
        // Create the request
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
//        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
//        request.httpBody = jsonData
        
        // Perform the HTTP request using URLSession
        URLSession.shared.dataTask(with: request) { [weak self] data, response, error in
            DispatchQueue.main.async {
                if let error = error {
                    self?.errorMessage = "Network request failed: \(error.localizedDescription)"
                    return
                }
                
                guard let data = data else {
                    self?.errorMessage = "No data received"
                    return
                }
                
                print(data)
                if let result = String(bytes: data, encoding: String.Encoding.ascii) {
                    print(result)
                }
                // Decode the JSON response using the LoginResponse model
//                do {
//                    let response = try JSONDecoder().decode(LoginResponse.self, from: data)
//                    
//                    if response.success {
//                        self?.errorMessage = "" // Clear any previous error messages
//                        print(response.message)
//                        // Proceed with any post-login actions (e.g., navigate to another view)
//                    } else {
//                        self?.errorMessage = "Login failed. \(response.message)"
//                    }
//                } catch {
//                    self?.errorMessage = "Failed to parse response"
//                }
            }
        }.resume()
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
        
        guard email.contains("@") && email.contains(".") else {
            errorMessage = "Please enter valid email."
            return false
        }
        
        print("Trying to log in")
        
        return true
    }
}

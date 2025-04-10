//
//  RegistrationViewViewModel.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

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
        
        // Send registration data to the backend
        sendRegistrationRequest(firstName: firstName, lastName: lastName, email: email, password: password)
    }
    
    private func sendRegistrationRequest(firstName: String, lastName: String, email: String, password: String) {
        // Your backend API URL for registration
        guard let url = URL(string: "http://192.168.10.101:5000") else {
            errorMessage = "Invalid URL"
            return
        }
        
        // Prepare the HTTP request body
        let body: [String: Any] = [
            "firstName": firstName,
            "lastName": lastName,
            "email": email,
            "password": password
        ]
        
        guard let jsonData = try? JSONSerialization.data(withJSONObject: body) else {
            errorMessage = "Failed to create request body"
            return
        }
        
        // Create the request
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = jsonData
        
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
                
                // Decode the JSON response using the RegistrationResponse model
                do {
                    let response = try JSONDecoder().decode(RegistrationResponse.self, from: data)
                    print("Blyat")
                    
                    if response.success {
                        self?.errorMessage = "" // Clear any previous error messages
                        // Proceed with any post-registration actions (e.g., navigate to login screen)
                    } else {
                        self?.errorMessage = "Registration failed. \(response.message)"
                    }
                } catch {
                    self?.errorMessage = "Failed to parse response"
                }
            }
        }.resume()
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

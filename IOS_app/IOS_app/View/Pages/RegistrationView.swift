//
//  RegistrationView.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//
import SwiftUI

struct RegistrationView: View {
    
    @StateObject var viewModel = RegistrationViewViewModel()
    @State private var showImagePicker = false
    @State private var selectedImageData: Data? = nil
    
    var body: some View {
        VStack {
            Form {
                TextField("Name" , text: $viewModel.name)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                
                TextField("Email" , text: $viewModel.email)
                    .autocapitalization(.none)
                    .autocorrectionDisabled()
                
                SecureField("Password" , text: $viewModel.password)
                
                Button("Select Profile Photo") {
                    showImagePicker.toggle()
                }
                
                if let selectedImageData, let uiImage = UIImage(data: selectedImageData) {
                    Image(uiImage: uiImage)
                        .resizable()
                        .scaledToFit()
                        .frame(width: 100, height: 100)
                        .clipShape(Circle())
                        .padding()
                }
                
                ButtonView(color: .blue, title: "Create account") {
                    if let imageData = selectedImageData {
                        if let imagePath = saveImageToLocalStorage(imageData) {
                            viewModel.profilePhoto = imagePath
                        }
                    }
                    viewModel.registration()
                }
                
                if !viewModel.errorMessage.isEmpty {
                    Text(viewModel.errorMessage)
                        .foregroundColor(Color.orange)
                        .multilineTextAlignment(.center)
                }
            }
            .padding(.top, 200)
        }
        .sheet(isPresented: $showImagePicker) {
            ImagePicker(isPresented: $showImagePicker, imageData: $selectedImageData)
        }
    }
    
    private func saveImageToLocalStorage(_ imageData: Data) -> String? {
        let fileName = UUID().uuidString
        let fileURL = getDocumentsDirectory().appendingPathComponent(fileName)
        do {
            try imageData.write(to: fileURL)
            return fileURL.path
        } catch {
            print("Error saving image: \(error)")
            return nil
        }
    }
    
    private func getDocumentsDirectory() -> URL {
        let paths = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        return paths[0]
    }
}

#Preview {
    RegistrationView()
}

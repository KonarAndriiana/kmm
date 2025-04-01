//
//  ImagePicker.swift
//  IOS_app
//
//  Created by Andriiana Konar on 31/03/2025.
//
import Foundation
import UIKit
import SwiftUI

struct ImagePicker: UIViewControllerRepresentable {
    
    @Binding var selectedImage : UIImage?
    @Binding var isPickerShow : Bool
    
    func makeUIViewController(context: Context) -> UIImagePickerController {
        let imagePicker = UIImagePickerController()
        imagePicker.sourceType = .photoLibrary
        imagePicker.delegate = context.coordinator // správne použitie koordinátora
        
        return imagePicker
    }
    
    func updateUIViewController(_ uiViewController: UIImagePickerController, context: Context) {
        // code
    }
    
    func makeCoordinator() -> Coordinator {
        return Coordinator(self) // posle samu seba ako referenc
    }
}

class Coordinator: NSObject, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    var parent : ImagePicker
    
    init(_ picker: ImagePicker) {
    // bude vkladat vo format imagePicker binding na selectedImage , lebo kvoli classu sa nemoze dostat ku bindingu
        self.parent = picker
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        // Spustí sa, keď užívateľ vyberie fotku
        print("Image selected")
        
        if let image = info[UIImagePickerController.InfoKey.originalImage] as? UIImage{
            //vybrat fotku
            DispatchQueue.main.async{
                self.parent.selectedImage = image
            }
        }
        
        //zrusit vyberanie aj ked fotka neje vybrata
        parent.isPickerShow = false
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        // Užívateľ zrušil výber
        print("Cancelled")
        
        //zrusit vyberanie
        parent.isPickerShow = false
    }
}

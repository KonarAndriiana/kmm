//
//  ImagePickerView.swift
//  IOS_app
//
//  Created by Andriiana Konar on 31/03/2025.
//

import SwiftUI

struct ImagePickerView: View {
    
    @State var isPickerShow = false
    @State var selectedImage : UIImage?
    
    var body: some View {
        VStack {
            
            if selectedImage != nil {
                Image(uiImage: selectedImage!)
                    .resizable()
                    .frame(width: 200.0, height: 200.0)
            }

            
            Button{
                // ukaze sa vyber fotok
                
                isPickerShow = true
            } label:{
                Text("Select a photo")
            }
        }
        .sheet(isPresented: $isPickerShow, onDismiss: nil) {
            // image picker
            ImagePicker(selectedImage: $selectedImage, isPickerShow: $isPickerShow)
        }
    }
}

#Preview {
    ImagePickerView()
}

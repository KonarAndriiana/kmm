//
//  ImagePickerView.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
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

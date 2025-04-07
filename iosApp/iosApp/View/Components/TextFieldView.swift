//
//  TextFieldView.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct TextFieldView: View {
    var placeholder: String
    @Binding var text: String
    var isSecure: Bool = false
    
    @State private var isPasswordVisible: Bool = false
    
    var body: some View {
        HStack {
            if isSecure && !isPasswordVisible {
                SecureField(placeholder, text: $text)
            } else {
                TextField(placeholder, text: $text)
            }
            
            if isSecure {
                Button(action: {
                    isPasswordVisible.toggle()
                }) {
                    Image(systemName: isPasswordVisible ? "eye.slash" : "eye")
                        .foregroundColor(.gray)
                }
            }
        }
        .padding()
        .background(Color.white)
        .cornerRadius(8)
        .overlay(RoundedRectangle(cornerRadius: 30).stroke(Color.gray, lineWidth: 1))
        .padding(.horizontal, 15)
        
    }
}

#Preview {
    @State var sampleText = ""
    return TextFieldView(placeholder: "Enter text", text: $sampleText, isSecure: false)
}

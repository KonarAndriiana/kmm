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
            ZStack(alignment: .center) {
                if text.isEmpty {
                    Text(placeholder)
                        .foregroundColor(.gray)
                        .multilineTextAlignment(.center)
                }

                if isSecure && !isPasswordVisible {
                    SecureField("", text: $text)
                        .multilineTextAlignment(.center)
                        .foregroundColor(.gray)
                } else {
                    TextField("", text: $text)
                        .multilineTextAlignment(.center)
                        .foregroundColor(.gray)
                }
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
        .background(
            RoundedRectangle(cornerRadius: 30)
                .fill(Color.white)
        )
//        .overlay(
//            RoundedRectangle(cornerRadius: 30)
//                .stroke(Color.gray, lineWidth: 1)
//        )
        .padding(.horizontal, 20)
    }
}

#Preview {
    @State var sampleText = ""
    return TextFieldView(placeholder: "password", text: $sampleText, isSecure: true)

}

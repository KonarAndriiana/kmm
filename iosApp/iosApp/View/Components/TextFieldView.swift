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
        ZStack(alignment: .leading) {

            if text.isEmpty {
                Text(placeholder)
                    .foregroundColor(.gray)
                    .padding(.leading, 16)
            }

            HStack {
                if isSecure && !isPasswordVisible {
                    SecureField("", text: $text)
                        .foregroundColor(.black)
                        .padding(.leading, 10)
                } else {
                    TextField("", text: $text)
                        .foregroundColor(.black)
                        .padding(.leading, 10)
                }

                if isSecure {
                    Button(action: {
                        isPasswordVisible.toggle()
                    }) {
                        Image(systemName: isPasswordVisible ? "eye.slash" : "eye")
                            .foregroundColor(.gray)
                    }
                    .padding(.trailing, 10)
                }
            }
        }
        .frame(height: 50)
        .background(
            RoundedRectangle(cornerRadius: 30)
                .fill(Color.white)
        )
        .padding(.horizontal, 20)
    }
}

#Preview {
    @State var sampleText = ""
    return TextFieldView(placeholder: "password", text: $sampleText, isSecure: true)
}

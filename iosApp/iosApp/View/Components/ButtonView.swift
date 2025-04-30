//
//  ButtonView.swift
//  iosApp
//
//  Created by Andriiana Konar on 07/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct ButtonView: View {
    let title: String
    let code: () -> Void

    var body: some View {
        Button {
            code()
        } label: {
            ZStack {
                RoundedRectangle(cornerRadius: 10)
                    .fill(Color.clear) 
                    .overlay(
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(Color.white, lineWidth: 3)
                    )

                Text(title)
                    .foregroundColor(Color.white)
                    .bold()
            }
            .padding(.all, 10)
        }
    }
}

#Preview {
    ButtonView(title: "Hello") {
        // code
    }
}

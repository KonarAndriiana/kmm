//
//  HeaderView.swift
//  iosApp
//
//  Created by Andriiana Konar on 22/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct HeaderView: View {
    var body: some View {
        VStack(spacing: 20) {
            Text("Welcome")
                .font(.system(size: 35))
                .multilineTextAlignment(.center)
                .foregroundColor(Color.white)
                .bold()
                .accessibilityIdentifier("welcome_text")
            
            HStack{
                Text("back to")
                    .foregroundColor(Color.white)
                    .font(.system(size: 25))
                
                Text("Bugless")
                    .font(.system(size: 25))
                    .foregroundColor(Color.white)
                    .bold()
            }
            .accessibilityIdentifier("bugless_text")
        }
    }
}

#Preview {
    HeaderView()
}

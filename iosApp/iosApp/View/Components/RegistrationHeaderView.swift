//
//  RegistrationHeaderView.swift
//  iosApp
//
//  Created by Andriiana Konar on 22/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct RegistrationHeaderView: View {
    var body: some View {
        VStack{
            Text("Create account")
                .font(.system(size: 35))
                .multilineTextAlignment(.center)
                .foregroundColor(Color.white)
                .bold()
                .accessibilityIdentifier("create_acc_text")
        }
    }
}

#Preview {
    RegistrationHeaderView()
}

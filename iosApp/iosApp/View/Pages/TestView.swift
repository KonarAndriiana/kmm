//
//  TestView.swift
//  iosApp
//
//  Created by Andriiana Konar on 15/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct TestView: View {
    
    @StateObject var viewModel = RegistrationViewViewModel()
    @State private var showToast = false
    
    var body: some View {
        NavigationView{
            ZStack{
                Image("backgroundImage")
                    .resizable()
                    .scaledToFill()
                    .ignoresSafeArea()
                    .accessibilityIdentifier("background_image")
                
                VStack(spacing: 150){
                    RegistrationHeaderView()
                    
                    RegistrationInputView()
                    
                    RegistrationBttnView()
                }
                .overlay(
                    VStack {
                        if showToast {
                            HStack {
                                Text(viewModel.errorMessage)
                                    .font(.body)
                                    .foregroundColor(.white)
                                    .padding()
                                    .frame(maxWidth: .infinity)
                                    .background(Color.gray)
                                    .cornerRadius(10)
                                    .shadow(radius: 10)
                            }
                            .padding(.horizontal)
                            .frame(maxWidth: 400)
                            .transition(.opacity)
                            .zIndex(1)
                            .onAppear {
                                DispatchQueue.main.asyncAfter(deadline: .now() + 5) {
                                    withAnimation {
                                        showToast = false
                                    }
                                }
                            }
                        }
                        Spacer()
                    }
                        .accessibilityIdentifier("error_msg")
                )
                .onChange(of: viewModel.errorMessage) { newError in
                    if !newError.isEmpty {
                        showToast = true
                    }
                }
            }
        }
    }
}

#Preview {
    TestView()
}

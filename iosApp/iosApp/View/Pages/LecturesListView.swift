//
//  LectureListView.swift
//  iosApp
//
//  Created by Andriiana Konar on 06/05/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LecturesListView: View {
    let course: Course

    var body: some View {
        VStack {
            Text("Lectures for \(course.name)")
                .font(.title)
                .padding()
            // Tu bude zoznam prednášok
        }
        .navigationTitle(course.name)
    }
}


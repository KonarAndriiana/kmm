//
//  CourseViewModel.swift
//  iosApp
//
//  Created by Andriiana Konar on 14/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared
 
@MainActor
class CourseViewModel: ObservableObject {
    @Published var courses: [Course] = []
    
    private let api = CourseApi(client: HttpClientProvider().getClient())

    func fetchCourses() {
        Task {
            do {
                let fetchedCourses = try await api.getCourses()
                self.courses = fetchedCourses
            } catch {
                print("❌ Error: \(error)")
            }
        }
    }
}

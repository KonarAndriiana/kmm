//
//  CourseViewModel.swift
//  iosApp
//
//  Created by Andriiana Konar on 14/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import shared

@MainActor
class CourseViewModel: ObservableObject {
    @Published var courses: [Course] = []
    private let courseApi: CourseApi

    init() {
        let client = HttpClientProvider().getClient()
        self.courseApi = CourseApi(client: client)
    }
        
    func fetchCourses() {
        Task {
            do {
                let result = try await courseApi.getCourses()
                self.courses = result
            } catch {
                print("Failed to fetch courses: \(error)")
            }
        }
    }
}



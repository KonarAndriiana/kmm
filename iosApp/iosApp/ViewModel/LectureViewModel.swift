//
//  LectureViewModel.swift
//  iosApp
//
//  Created by Andriiana Konar on 30/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import shared


@MainActor
class LectureViewModel: ObservableObject {
    
    @Published var lectures : [LectureSummary] = []
    private let lectureApi: LectureApi
    
    init() {
        let client = HttpClientProvider().getClient()
        self.lectureApi = LectureApi(client: client)
    }
    
    func fetchLectures() {
        Task {
            do {
                let result = try await lectureApi.getLectures()
                self.lectures = result
            }catch{
                print("Failed to fetch courses: \(error)")
            }
        }
    }
}

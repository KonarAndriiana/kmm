//
//  LectureDetailView.swift
//  iosApp
//
//  Created by Andriiana Konar on 30/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import shared

@MainActor
class LectureDetailView: ObservableObject {
    @Published var courses : [Lecture] = []
    private var lectureApi : LectureApi
    
    init() {
        let client = HttpClientProvider().getClient()
        self.lectureApi = LectureApi(client: client)
    }
    
    func fetchLectureDetail() {
        Task{
            do{
                let result = try await lectureApi.getLectureById(id: <#T##String#>)
                self.lectureApi = result ?? String
            }catch{
                print("Failed to fetch lectures details: \(error)")
            }
        }
    }
}

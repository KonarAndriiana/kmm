//
//  LectureDetailView.swift
//  iosApp
//
//  Created by Andriiana Konar on 30/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import shared

@MainActor
class LectureDetailModel: ObservableObject {
    @Published var lecture : Lecture? = nil
    private var lectureApi : LectureApi
    
    init() {
        let client = HttpClientProvider().getClient()
        self.lectureApi = LectureApi(client: client)
    }
    
    func fetchLectureDetail(id: String) {
        Task{
            do{
                let result = try await lectureApi.getLectureById(id: id)
                self.lecture = result
            }catch{
                print("Failed to fetch lectures details: \(error)")
            }
        }
    }
}

//
//  Courses.swift
//  iosApp
//
//  Created by Andriiana Konar on 14/05/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared

struct Courses: Identifiable {
    let id = UUID()
    let name: String
    let specification: String
    let level: String
//    let photo: String

    init(from course: Course) {
        self.name = course.name
        self.specification = course.specification
        self.level = course.level
//
//        switch course.specification.lowercased() {
//        case "backend":
//            self.photo = "bg_1"
//        case "frontend":
//            self.photo = "bg_2"
//        case "security":
//            self.photo = "bg_0"
//        case "database":
//            self.photo = "bg_4"
//        default:
//            self.photo = "bg_3"
//        }
    }
}


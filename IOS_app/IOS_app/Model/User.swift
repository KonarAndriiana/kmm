//
//  User.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//

import Foundation

struct User: Codable {
    let id: String
    let firstName: String
    let lastName: String
    let email: String
    let joined: TimeInterval
}

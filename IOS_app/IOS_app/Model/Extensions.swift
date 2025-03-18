//
//  Extensions.swift
//  IOS_app
//
//  Created by Andriiana Konar on 18/03/2025.
//

import Foundation

extension Encodable {
    func asDictionary() -> [String : Any] {
        guard let data = try? JSONEncoder().encode(self) else {
            return[:] //empty dictionary
        }
        
        do {
            let json = try JSONSerialization.jsonObject(with: data) as? [String : Any]
            return json ?? [:]
        } catch {
            return [:]
        }
    }
}

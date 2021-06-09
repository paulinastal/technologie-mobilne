//
//  WeatherAppApp.swift
//  WeatherApp
//
//  Created by Paulina Stal on 5/7/21.
//

import SwiftUI

@main
struct WeatherAppApp: App {
    var viewModel = WeatherViewModel()
    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: viewModel)
        }
    }
}

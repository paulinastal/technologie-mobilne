//
//  WeatherViewModel.swift
//  WeatherApp
//
//  Created by Paulina Stal on 5/7/21.
//

import Foundation

class WeatherViewModel: ObservableObject {
    
    @Published private(set) var model: WeatherModel = WeatherModel(cities: ["Venice", "Paris", "Warsaw", "Berlin", "Barcelona", "London", "Prague", "New York"])
    
    var records: Array<WeatherModel.WeatherRecord> {
        model.records
    }
    
    func refresh(record: WeatherModel.WeatherRecord) {
        model.refresh(record: record)
    }
    
    func changeParam(record: WeatherModel.WeatherRecord) {
        model.changeParam(record: record)
    }
}

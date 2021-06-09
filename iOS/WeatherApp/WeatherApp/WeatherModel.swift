//
//  WeatherModel.swift
//  WeatherApp
//
//  Created by Paulina Stal on 5/7/21.
//

import Foundation

struct WeatherModel {
    
    var records: Array<WeatherRecord> = []
    
    init(cities: Array<String>) {
        records = Array<WeatherRecord>()
        for city in cities {
            let temperature = Float.random(in: -10.0 ... 30.0)
            let currentParam = String(format: "Temperature %.1f ℃", temperature)
            records.append(WeatherRecord(cityName: city, temperature: temperature, currentParam: currentParam))
            records[records.count-1].icon = setIcon(state: records[records.count-1].weatherState)
        }
    }
    
    struct WeatherRecord: Identifiable {
        var id: UUID = UUID()
        var icon: String = "☀️"
        var cityName: String
        var weatherState: String = ["Clear", "Snow", "Sleet",
                                    "Hail", "Thunderstorm", "Heavy Rain",
                                    "Light Rain", "Showers", "Heavy Cloud",
                                    "Light Cloud"].randomElement()!
        var temperature: Float
        var humidity: Float = Float.random(in: 0 ... 100)
        var windSpeed: Float = Float.random(in: 0 ..< 44)
        var windDirection: Float = Float.random(in: 0 ..< 360)
        var currentState: Int = 0
        var currentParam: String
    }
    
    func setIcon(state: String) -> String {
        switch state {
        case "Clear":
            return "☀️"
        case "Snow":
            return "❄️"
        case "Sleet":
            return "🌧"
        case "Hail":
            return "🌨"
        case "Thunderstorm":
            return "🌩"
        case "Heavy Rain":
            return "🌧"
        case "Light Rain":
            return "🌧"
        case "Showers":
            return "🌦"
        case "Heavy Cloud":
            return "☁️"
        case "Light Cloud":
            return "🌥"
        default: break
        }
        return "😢"
    }
    
    mutating func refresh(record: WeatherRecord) {
        for (index, item) in records.enumerated() {
            if item.cityName == record.cityName {
                records[index].temperature = Float.random(in: -10.0 ... 30.0)
                records[index].humidity = Float.random(in: 0 ... 100)
                records[index].windDirection = Float.random(in: 0 ..< 360)
                records[index].windSpeed = Float.random(in: 0 ..< 44)
                records[index].weatherState = ["Clear", "Snow", "Sleet",
                                               "Hail", "Thunderstorm", "Heavy Rain",
                                               "Light Rain", "Showers", "Heavy Cloud",
                                               "Light Cloud"].randomElement()!
                records[index].icon = setIcon(state: records[index].weatherState)
            }
        }
        print("Refreshing record: \(record)")
    }
    
    mutating func changeDisplayParam(selectedItem: Int) {
        let paramNo = records[selectedItem].currentState
        if(paramNo == 0){
            records[selectedItem].currentParam = String(format: "Temperature %.1f ℃", records[selectedItem].temperature)
        } else if(paramNo == 1) {
            records[selectedItem].currentParam = String(format: "Humidity %.2f%%", records[selectedItem].humidity)
        } else if (paramNo == 2){
            records[selectedItem].currentParam = String(format: "Wind %.2f mph", records[selectedItem].windSpeed)
        } else {
            records[selectedItem].currentParam = String(format: "Direction %.2f º", records[selectedItem].windDirection)
        }
    }
    
    mutating func changeParam(record: WeatherRecord) {
        let currentParam = record.currentState
        var next = currentParam + 1
        
        if(next > 3) {
            next = 0
        }
        
        switch next {
        case 0:
            if let selectedRec = records.firstIndex(where: {$0.id == record.id}) {
                records[selectedRec].currentState = next
                changeDisplayParam(selectedItem: selectedRec)
            }
        case 1:
            if let selectedRec = records.firstIndex(where: {$0.id == record.id}) {
                records[selectedRec].currentState = next
                changeDisplayParam(selectedItem: selectedRec)
            }
        case 2:
            if let selectedRec = records.firstIndex(where: {$0.id == record.id}) {
                records[selectedRec].currentState = next
                changeDisplayParam(selectedItem: selectedRec)
            }
        case 3:
            if let selectedRec = records.firstIndex(where: {$0.id == record.id}) {
                records[selectedRec].currentState = next
                changeDisplayParam(selectedItem: selectedRec)
            }        default: break
        }
    }
}

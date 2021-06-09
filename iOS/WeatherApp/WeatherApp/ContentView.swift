//
//  ContentView.swift
//  WeatherApp
//
//  Created by Paulina Stal on 5/7/21.
//

import SwiftUI

struct ContentView: View {
    
    @ObservedObject var viewModel: WeatherViewModel
    
    var body: some View {
        // mozliowsc przewijania widokiem
        ScrollView(.vertical){
            VStack {
                ForEach(viewModel.records){record in
                    WeatherRecordView(record: record,
                                      viewModel: viewModel)
                }
            }.padding()
        }
    }
}

struct WeatherRecordView: View {
    var record: WeatherModel.WeatherRecord
    var viewModel: WeatherViewModel
    var body: some View {
        let rectangleCornerRadius:CGFloat = 25.0
        // parametrry odpowiedzialne za wysokosc komorek
        let frameWidth:CGFloat = 280
        let frameHeight:CGFloat = 100
        let miniFrame:CGFloat = 50

        ZStack {
            RoundedRectangle(cornerRadius: rectangleCornerRadius)
                .stroke(lineWidth: 0.5)
            HStack{
                VStack{
                    // ikonka zajmuje cala powierzchnie
                    Text(record.icon)
                        .font(.largeTitle)
                        .frame(maxWidth: .infinity,
                               maxHeight: .infinity,
                               alignment: .bottomLeading)
                }
                // ikonka wyrownana do lewej strony
                .fixedSize(horizontal: true, vertical: /*@START_MENU_TOKEN@*/true/*@END_MENU_TOKEN@*/)
                .frame(width: miniFrame,
                       height: miniFrame,
                       alignment: .bottomLeading)
                
                VStack(alignment: .leading, spacing: 5) {
                    // parametry oraz nazwa miasta wyrownane
                    // wzgledem siebie do lewej strony
                    // dodatkowo zostal zwiekszony odstep pomiedzy nimi
                    Text(record.cityName)
                    Text(record.currentParam)
                        .font(.caption)
                        .onTapGesture {
                            viewModel.changeParam(record: record)
                        }
                }
                .frame(width: frameHeight * 1.2,
                       height: miniFrame,
                       alignment: .center)
                VStack{
                    // ikonka odswiezania wyrownana do lewej strony
                    Text("⟳")
                        .font(.largeTitle)
                        .frame(maxWidth: .infinity,
                               maxHeight: .infinity,
                               alignment: .bottomTrailing)
                        .onTapGesture {
                            viewModel.refresh(record: record)}
                }
                .fixedSize(horizontal: true, vertical: /*@START_MENU_TOKEN@*/true/*@END_MENU_TOKEN@*/)
                .frame(width: miniFrame,
                       height: miniFrame,
                       alignment: .bottomTrailing)
            }
            .frame(width: frameWidth, height: frameHeight, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
        }
        .frame(width: frameWidth, height: frameHeight, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
        
    }

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(viewModel: WeatherViewModel())
    }
}
}


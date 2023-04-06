//
//  HomeView.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-04-02.
//

import SwiftUI

struct HomeView: View {
    
    @State var timeNow = ""
            let timer = Timer.publish(every: 1, on: .main, in: .common).autoconnect()
            var dateFormatter: DateFormatter {
                    let fmtr = DateFormatter()
                    fmtr.dateFormat = "HH:mm"
                    return fmtr
            }
    
    @EnvironmentObject var viewModel: ViewModel     //Hämtar controller
            
    var body: some View {
        
        VStack {
            
            Text(timeNow)
                .onReceive(timer) { _ in
                    self.timeNow = dateFormatter.string(from: Date())
                }
                .font(.largeTitle)
                .bold()
                .foregroundColor(Color.white)
            
            
            //Play button is showed when not clocked and toggles both below when clocked
            Button("IN") {
                viewModel.toggleClock(state: .inClock)
            }
            .buttonStyle(.bordered)
            .background(Color.green)
            .cornerRadius(/*@START_MENU_TOKEN@*/8.0/*@END_MENU_TOKEN@*/)
            
            HStack {
                
                Button("RAST") {
                    viewModel.toggleClock(state: .breakClock)
                }
                .buttonStyle(.bordered)
                .background(Color.yellow)
                .cornerRadius(/*@START_MENU_TOKEN@*/8.0/*@END_MENU_TOKEN@*/)
                
                Button("UT") {
                    viewModel.toggleClock(state: .outClock)
                }
                .buttonStyle(.bordered)
                .background(Color.red)
                .cornerRadius(/*@START_MENU_TOKEN@*/8.0/*@END_MENU_TOKEN@*/)
            }
            //.hidden()

            
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity) // 1
        .accentColor(Color.white)
        .background(Color.black)
    }
    
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
        
    }
}

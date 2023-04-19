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
            
                
                Button(action: {
                    viewModel.toggleClock(state: .inClock)
                }, label: {
                    ZStack {
                        Rectangle()
                            .frame(width: 100, height: 50)
                            .foregroundColor(.green)
                        .cornerRadius(8.0)
                        HStack {
                            Image(systemName: "play")
                            Text("IN")
                        }
                    }
                })
                //.buttonStyle(.bordered)
                
            .frame(width: 100)
            
            
            HStack {
                ButtonsView(title1: "RAST", title2: "UT", color1: Color(hue: 0.129, saturation: 0.871, brightness: 0.902), color2: Color.red)
                
                
            }
            .frame(width: 200)
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

struct ButtonsView: View {
    var title1: String
    var title2: String
    var color1: Color
    var color2: Color
    var body: some View {
        HStack {
            Button(action: {
                //viewModel.toggleClock(state: .breakClock)
            }, label: {
                ZStack {
                    Rectangle()
                        .frame(width: 100, height: 50)
                        .foregroundColor(color1)
                        .cornerRadius(8.0)
                    HStack {
                        Image(systemName: "pause")
                        Text(title1)
                    }
                }
            })
            Button(action: {
                //viewModel.toggleClock(state: .outClock)
            }, label: {
                ZStack {
                    Rectangle()
                        .frame(width: 100, height: 50)
                        .foregroundColor(color2)
                    .cornerRadius(8.0)
                    HStack {
                        Image(systemName: "stop")
                        Text(title2)
                    }
                }
            })
        }
    }
}

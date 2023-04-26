//
//  HomeView.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-04-02.
//

import SwiftUI

struct HomeView: View {
    
    @State var isClockedIn = false
    
    var body: some View {
        
        //Navigation View to have history view fullscreen
        NavigationView {
            
<<<<<<< Updated upstream
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
=======
            VStack {
                
                Spacer()
                
                ClockView()             //Clock showing current time
                
                if !isClockedIn {
                    InButtonView(isClockedIn: $isClockedIn)      //Button for clocking in
                }
                else {
                    TimerView()
                    BreakOutButtonsView(isClockedIn: $isClockedIn)   //Buttons for stopping the clock
                }
>>>>>>> Stashed changes

                Spacer()
                
                //Link to HistoryView
                NavigationLink(destination: HistoryView()) {
                    ZStack {
                        Rectangle()
                            .frame(width: 140, height: 50)
                            .foregroundColor(.blue)
                            .cornerRadius(8.0)
                        HStack {
                            Image(systemName: "clock")
                            Text("HISTORIK")
                                .foregroundColor(Color.white)
                            }
                        }
                    }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .accentColor(Color.white)
            .background(Color.black)
        }
    }
    
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
        
    }
}

<<<<<<< Updated upstream
struct ButtonsView: View {
    var title1: String
    var title2: String
    var color1: Color
    var color2: Color
    var body: some View {
        HStack {
            Button(action: {
                //viewModel.toggleClock(state: .breakClock)
=======
struct ClockView: View {
    
    @State var timeNow = ""
            let timer = Timer.publish(every: 1, on: .main, in: .common).autoconnect()
            var dateFormatter: DateFormatter {
                    let fmtr = DateFormatter()
                    fmtr.dateFormat = "HH:mm"
                    return fmtr
            }
    
    var body: some View {
        Text(timeNow)
            .onReceive(timer) { _ in
                self.timeNow = dateFormatter.string(from: Date())
            }
            .font(.largeTitle)
            .bold()
            .foregroundColor(Color.white)
    }
}

struct InButtonView: View {
    
    @Binding var isClockedIn: Bool
    
    var body: some View {
        
        Button(action: {
            //action
            isClockedIn = true
        }, label: {
            ZStack {
                Rectangle()
                    .frame(width: 100, height: 50)
                    .foregroundColor(Color.green)
                    .cornerRadius(8.0)
                HStack {
                    Image(systemName: "play")
                    Text("IN")
                }
            }
        })
    }
}

struct BreakOutButtonsView: View {
    
    @Binding var isClockedIn: Bool
    
    var body: some View {
        HStack {
            
            //Button for registering break
            Button(action: {
                //action
                isClockedIn = false
>>>>>>> Stashed changes
            }, label: {
                ZStack {
                    Rectangle()
                        .frame(width: 100, height: 50)
<<<<<<< Updated upstream
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
=======
                        .foregroundColor(Color.yellow)
                        .cornerRadius(8.0)
                    HStack {
                        Image(systemName: "pause")
                        Text("RAST")
                    }
                }
            })
            
            //Button for registering end of shift
            Button(action: {
                //action
                isClockedIn = false
>>>>>>> Stashed changes
            }, label: {
                ZStack {
                    Rectangle()
                        .frame(width: 100, height: 50)
<<<<<<< Updated upstream
                        .foregroundColor(color2)
                    .cornerRadius(8.0)
                    HStack {
                        Image(systemName: "stop")
                        Text(title2)
=======
                        .foregroundColor(Color.red)
                        .cornerRadius(8.0)
                    HStack {
                        Image(systemName: "stop")
                        Text("UT")
>>>>>>> Stashed changes
                    }
                }
            })
        }
    }
}
<<<<<<< Updated upstream
=======

struct TimerView: View {
    
    @State private var startTime: Date?
    @State private var elapsedTime: TimeInterval = 0.0
    
    private let timer = Timer.publish(every: 0.01, on: .main, in: .common).autoconnect()
    
    private var elapsedTimeString: String {
        let formatter = DateComponentsFormatter()
        formatter.zeroFormattingBehavior = [.pad]
        formatter.allowedUnits = [.hour, .minute, .second]
        formatter.unitsStyle = .positional
        formatter.includesTimeRemainingPhrase = false
        return formatter.string(from: elapsedTime)!
    }
    
    var body: some View {
        Text(elapsedTimeString)
            .font(.title)
            .onReceive(timer) { _ in
                if let startTime = startTime {
                    elapsedTime = Date().timeIntervalSince(startTime)
                }
            }
            .onAppear {
                startTime = Date()
            }
    }
}

>>>>>>> Stashed changes

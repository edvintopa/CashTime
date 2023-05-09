//
//  HomeView.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-04-02.
//

import SwiftUI

struct HomeView: View {
    
    @State private var isClockedIn = false
    @State private var selectedWorkplace = "Coop"
    @State private var showNewWorkplaceView = false
    
    var body: some View {
        
        //Navigation View to have history view fullscreen
        NavigationView {
            
            VStack {
                
                Spacer()
                
                ClockView()             //Clock showing current time
                
                if !isClockedIn {
                    WorkplaceSelectorView(selectedWorkplace: $selectedWorkplace, showNewWorkplaceView: $showNewWorkplaceView)
                    
                    InButtonView(isClockedIn: $isClockedIn)      //Button for clocking in
                }
                else {
                    TimerView()
                    BreakOutButtonsView(isClockedIn: $isClockedIn)   //Buttons for stopping the clock
                }

                Spacer()
                
                //Link to NewWorkplaceView
                NavigationLink(destination: NewWorkplaceView(), isActive: $showNewWorkplaceView) {
                    //no label
                }
                
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
            .environment(\.colorScheme, .dark)
    }
}

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
            }, label: {
                ZStack {
                    Rectangle()
                        .frame(width: 100, height: 50)
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
            }, label: {
                ZStack {
                    Rectangle()
                        .frame(width: 100, height: 50)
                        .foregroundColor(Color.red)
                        .cornerRadius(8.0)
                    HStack {
                        Image(systemName: "stop")
                        Text("UT")
                    }
                }
            })
        }
    }
}

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

struct WorkplaceSelectorView: View {
    @Binding var selectedWorkplace: String
    @Binding var showNewWorkplaceView: Bool
    
    var body: some View {
        Menu {
            
            //foreach workplace
            Button {
                selectedWorkplace = "ICA"
            } label: {
                Text("ICA")
            }
            
            Button {
                selectedWorkplace = "Coop"
            } label: {
                Text("Coop")
            }
            
            Button {
                showNewWorkplaceView = true     //trigger to activate view
            } label: {
                Image(systemName: "plus")
                Text("Ny arbetsplats")
            }
            
            
        } label: {
            Label {
                Text("\(selectedWorkplace)")
            } icon: {
                //none for now
            }
        }
    }
}

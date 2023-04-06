//
//  ViewModel.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-04-02.
//

import Foundation

class ViewModel: ObservableObject {     //Controller av views?
    @Published var isClockedIn = false;
    
    enum ClockState {
        case inClock
        case outClock
        case breakClock
    }
    
    func toggleClock(state: ClockState) {
        /*
         If state is IN && button RAST:
                endTime for work is also startTime for break
         
         If state 
         */
    }
}

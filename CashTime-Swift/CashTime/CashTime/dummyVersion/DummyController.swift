//
//  DummyController.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-05-24.
//

import Foundation

//Dummy class to show UI and basic functionallity
class DummyController : ObservableObject {
    @Published var isClockedIn = false;
    @Published var workplaces: [DummyWorkplace] = []
    
    //Create example workplace
    func createWorkplace() {
        workplaces.append(DummyWorkplace(name: "ICA", pay: 150.00))
        workplaces.append(DummyWorkplace(name: "MAU", pay: 1500.00))
    }
    
    //Create OBIntervals
    func createOBIntervals() {
        
    }
}

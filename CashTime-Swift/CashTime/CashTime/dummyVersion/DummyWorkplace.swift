//
//  DummyWorkplace.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-05-24.
//

import Foundation

//Dummy class to show UI and basic functionallity
class DummyWorkplace {
    private var name: String           // Name of workplace
    private var hourlyPay: Double      // Base hourly pay "grundlön"
    private var rateSchedule: [DummyOBInterval]
        
    init(name: String, pay: Double) {
        self.name = name
        self.hourlyPay = pay
        self.rateSchedule = []
    }
        
    func getName() -> String {
        return name
    }
        
    func getPay() -> Double {
    
    return hourlyPay
    }
        
    func getRateSchedule() -> [DummyOBInterval] {
        return rateSchedule
    }
    
    func addToRateSchedule(interval: DummyOBInterval) {
        rateSchedule.append(interval)
    }
}

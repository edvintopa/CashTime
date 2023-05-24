//
//  DummyOBInterval.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-05-24.
//

import Foundation

//Dummy class to show UI and basic functionallity
class DummyOBInterval: DummyInterval {
    private var extraPay: Double
    
    init(start: Date, end: Date, extraPay: Double) {
        self.extraPay = extraPay
        super.init(start: start, end: end)
    }
    
    func getExtraPay() -> Double {
        return extraPay
    }
    
    func getTimeSpentInOBInterval(interval: DummyInterval) -> TimeInterval {
            var tempInterval = interval
            if interval.getStart() < self.getStart() { // If starts before OB
                tempInterval.setStart(start: self.getStart())
            }
            if interval.getEnd() > self.getEnd() { // If ends after OB
                tempInterval.setEnd(end: self.getEnd())
            }
            return tempInterval.getDuration()
        }
}

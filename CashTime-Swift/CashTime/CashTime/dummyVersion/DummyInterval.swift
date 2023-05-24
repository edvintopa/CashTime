//
//  DummyInterval.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-05-24.
//

import Foundation

//Dummy class to show UI and basic functionallity
class DummyInterval {
    private var start: Date
    private var end: Date
    
    init(start: Date, end: Date) {
        self.start = start
        self.end = end
    }
    
    func getStart() -> Date {
        return start
    }
    
    func getEnd() -> Date {
        return end
    }
    
    func setStart(start:Date) {
        self.start = start
    }
    
    func setEnd(end:Date) {
        self.end = end
    }
    
    func getDuration() -> TimeInterval {
        return end.timeIntervalSince(start)
    }
    
}

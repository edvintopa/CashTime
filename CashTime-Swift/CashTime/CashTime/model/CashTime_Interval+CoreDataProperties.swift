//
//  CashTime_Interval+CoreDataProperties.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-05-09.
//
//

import Foundation
import CoreData


extension CashTime_Interval {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<CashTime_Interval> {
        return NSFetchRequest<CashTime_Interval>(entityName: "CashTime_Interval")
    }

    @NSManaged public var start: Date?
    @NSManaged public var end: Date?
    @NSManaged public var id: UUID?
    @NSManaged public var workplace: CashTime_Workplace?

}

extension CashTime_Interval : Identifiable {

}

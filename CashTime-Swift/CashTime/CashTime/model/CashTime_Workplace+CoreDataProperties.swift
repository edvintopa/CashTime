//
//  CashTime_Workplace+CoreDataProperties.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-05-09.
//
//

import Foundation
import CoreData


extension CashTime_Workplace {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<CashTime_Workplace> {
        return NSFetchRequest<CashTime_Workplace>(entityName: "CashTime_Workplace")
    }

    @NSManaged public var obIntervals: NSObject?
    @NSManaged public var name: String?
    @NSManaged public var id: UUID?
    @NSManaged public var pay: Double
    @NSManaged public var intervals: NSSet?

}

// MARK: Generated accessors for intervals
extension CashTime_Workplace {

    @objc(addIntervalsObject:)
    @NSManaged public func addToIntervals(_ value: CashTime_Interval)

    @objc(removeIntervalsObject:)
    @NSManaged public func removeFromIntervals(_ value: CashTime_Interval)

    @objc(addIntervals:)
    @NSManaged public func addToIntervals(_ values: NSSet)

    @objc(removeIntervals:)
    @NSManaged public func removeFromIntervals(_ values: NSSet)

}

extension CashTime_Workplace : Identifiable {

}

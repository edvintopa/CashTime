//
//  Interval+CoreDataProperties.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-05-07.
//
//

import Foundation
import CoreData


extension Interval {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<Interval> {
        return NSFetchRequest<Interval>(entityName: "Interval")
    }

    @NSManaged public var start: Date?
    @NSManaged public var end: Date?
    @NSManaged public var id: UUID?
    @NSManaged public var workplace: Workplace?

}

extension Interval : Identifiable {

}

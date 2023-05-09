//
//  CashTime_OBInterval+CoreDataProperties.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-05-09.
//
//

import Foundation
import CoreData


extension CashTime_OBInterval {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<CashTime_OBInterval> {
        return NSFetchRequest<CashTime_OBInterval>(entityName: "CashTime_OBInterval")
    }

    @NSManaged public var extraPay: Double
    @NSManaged public var dayOfWeek: String?

}

//
//  HistoryView.swift
//  CashTime
//
//  Created by Edvin Topalovic on 2023-04-26.
//

import SwiftUI

struct HistoryView: View {
    @State private var selectedDate: Date?
    @State private var selectedWorkplace: String?
    
    let exampleData: [Workday] = [
        Workday(date: Date(), workplace: "ICA", hoursWorked: 5.5, obIntervals: [
            OBInterval(startTime: "06:00", endTime: "08:30", extraPay: 0.0),
            OBInterval(startTime: "09:00", endTime: "12:00", extraPay: 15.0)
        ], hourlyPay: 20.0),
        Workday(date: Date().addingTimeInterval(-86400), workplace: "Coop", hoursWorked: 7.0, obIntervals: [
            OBInterval(startTime: "10:00", endTime: "13:00", extraPay: 12.0),
            OBInterval(startTime: "14:00", endTime: "16:30", extraPay: 20.0)
        ], hourlyPay: 22.2),
        Workday(date: Date().addingTimeInterval(-172800), workplace: "ICA", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-259200), workplace: "ICA", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-345600), workplace: "ICA", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-432000), workplace: "Coop", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-518400), workplace: "ICA", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-604800), workplace: "ICA", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-691200), workplace: "Coop", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-777600), workplace: "Biltema", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-864000), workplace: "ICA", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-950400), workplace: "ICA", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-1036800), workplace: "Biltema", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-1123200), workplace: "ICA", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-1209600), workplace: "Biltema", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
        Workday(date: Date().addingTimeInterval(-1296000), workplace: "Biltema", hoursWorked: 6.5, obIntervals: [
            OBInterval(startTime: "11:00", endTime: "14:00", extraPay: 8.0),
            OBInterval(startTime: "15:00", endTime: "17:00", extraPay: 13.0)
        ], hourlyPay: 17.8),
    ]
    
    var filteredData: [Workday] {
        var filtered = exampleData
        
        if let selectedDate = selectedDate {
            filtered = filtered.filter { Calendar.current.isDate($0.date, inSameDayAs: selectedDate) }
        }
        
        if let selectedWorkplace = selectedWorkplace {
            filtered = filtered.filter { $0.workplace == selectedWorkplace }
        }
        
        return filtered
    }
    
    var body: some View {
        VStack {
            FilterView(selectedDate: $selectedDate, selectedWorkplace: $selectedWorkplace)
            
            List(filteredData.sorted(by: { $0.date > $1.date })) { workday in
                NavigationLink(destination: WorkdayDetailView(workday: workday)) {
                    VStack(alignment: .leading) {
                        Text(workday.date, style: .date)
                            .font(.headline)
                        Text("Arbetsplats: \(workday.workplace)")
                        Text("Arbetade timmar: \(workday.hoursWorked)")
                    }
                }
            }
        }
        .padding()
    }
}

struct FilterView: View {
    @Binding var selectedDate: Date?
    @State private var filterDate: Date = Date()
    @Binding var selectedWorkplace: String?
    
    let workplaces = ["ICA", "Coop", "Biltema"]
    
    var body: some View {
        HStack {
            DatePicker("Filter", selection: $filterDate, displayedComponents: .date)
                .datePickerStyle(.compact)
            
            Picker("Välj arbetsplats", selection: $selectedWorkplace) {
                Text("Alla arbetsplatser").tag(nil as String?)
                ForEach(workplaces, id: \.self) { workplace in
                    Text(workplace).tag(workplace)
                }
            }
            .pickerStyle(.menu)
        }
        .padding(.bottom)
    }
}

struct WorkdayDetailView: View {
    let workday: Workday
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("Datum: \(workday.date, style: .date)")
                .font(.title)
            
            Text("Arbetade timmar: \(workday.hoursWorked)")
                .padding(.vertical)
            
            if !workday.obIntervals.isEmpty {
                Text("Intervaller:")
                    .font(.headline)
                
                ForEach(workday.obIntervals, id: \.self) { obInterval in
                    VStack(alignment: .leading) {
                        Text("Start: \(obInterval.startTime)")
                        Text("Slut: \(obInterval.endTime)")
                        Text("OB: \(obInterval.extraPay)")
                    }
                    .padding(.vertical)
                }
            }
            
            Text("Totalt tjänat: \(workday.totalPay)")
                .font(.title2)
                .padding(.top)
            
            Spacer()
        }
        .padding()
        .navigationBarTitle(workday.workplace)
    }
}

struct Workday: Identifiable {
    let id = UUID()
    let date: Date
    let workplace: String
    let hoursWorked: Double
    let obIntervals: [OBInterval]
    let hourlyPay: Double
    
    var totalPay: Double {
        let obPay = obIntervals.reduce(0.0) { $0 + $1.extraPay }
        return hoursWorked * hourlyPay + obPay
    }
}

struct OBInterval: Hashable {
    let startTime: String
    let endTime: String
    let extraPay: Double
}

struct HistoryView_Previews: PreviewProvider {
    static var previews: some View {
        HistoryView()
    }
}


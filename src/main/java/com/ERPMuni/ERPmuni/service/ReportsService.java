package com.ERPMuni.ERPmuni.service;

import com.ERPMuni.ERPmuni.repository.AttendanceRepository;
import com.ERPMuni.ERPmuni.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ReportsService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeesRepository employeesRepository;

    @Autowired
    public ReportsService(AttendanceRepository attendanceRepository, EmployeesRepository employeesRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeesRepository = employeesRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getReportsData() {
        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate monthEnd = today.withDayOfMonth(today.lengthOfMonth());

        long presentToday = attendanceRepository.countByStatusAndAttendanceDate("TEMPRANO", today);
        long lateToday = attendanceRepository.countByStatusAndAttendanceDate("TARDE", today);
        long absentToday = attendanceRepository.countByStatusAndAttendanceDate("FALTA", today);

        long monthTotal = attendanceRepository.countByAttendanceDateBetween(monthStart, monthEnd);
        long monthAbsences = attendanceRepository.countByStatusAndAttendanceDateBetween("FALTA", monthStart, monthEnd);
        double attendanceRateMonth = monthTotal == 0 ? 0.0 : ((monthTotal - monthAbsences) * 100.0) / monthTotal;

        long activeEmployees = employeesRepository.countAllEmployees();
        long inactiveEmployees = employeesRepository.findEmployeesModelByStatus("INACTIVO").size();
        long everHired = activeEmployees + inactiveEmployees;
        double turnoverRate = everHired == 0 ? 0.0 : (inactiveEmployees * 100.0) / everHired;

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("activeEmployees", activeEmployees);
        data.put("attendanceRateMonth", round1(attendanceRateMonth));
        data.put("absencesMonth", monthAbsences);
        data.put("turnoverRate", round1(turnoverRate));
        data.put("todayPresent", presentToday);
        data.put("todayLate", lateToday);
        data.put("todayAbsent", absentToday);
        return data;
    }

    private double round1(double value) {
        return Math.round(value * 10) / 10.0;
    }
}

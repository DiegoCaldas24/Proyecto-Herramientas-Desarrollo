package com.ERPMuni.ERPmuni.service;

import com.ERPMuni.ERPmuni.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AttendanceCronjob {
    @Autowired
    AttendanceService attendanceService;

    @Scheduled(cron = "0 59 23 * * *")
    public void cron() {
        attendanceService.procesarFaltasDelDia();
    }
}

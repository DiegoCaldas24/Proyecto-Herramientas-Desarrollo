package com.ERPMuni.ERPmuni.service;

import com.ERPMuni.ERPmuni.model.AttendanceModel;
import com.ERPMuni.ERPmuni.model.EmployeesModel;
import com.ERPMuni.ERPmuni.repository.AttendanceRepository;
import com.ERPMuni.ERPmuni.repository.EmployeesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AttendanceService{

    private final AttendanceRepository attendanceRepository;
    private final EmployeesRepository employeesRepository;
    private static final LocalTime HORA_ENTRADA = LocalTime.of(8, 15);


    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, EmployeesRepository employeesRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeesRepository = employeesRepository;
    }

    @Transactional
    public boolean saveAttendance(String dniEmployee) {
        try{
            LocalDate today = LocalDate.now();
            LocalDateTime hour = LocalDateTime.now();
            AttendanceModel attendanceToday = attendanceRepository
                    .findByEmployees_DniAndAttendanceDate(dniEmployee, today)
                    .orElse(null);

            if(attendanceToday == null) {
                AttendanceModel attendanceModel = new AttendanceModel();
                attendanceModel.setEmployees(employeesRepository.findByDni(dniEmployee).get());
                attendanceModel.setAttendanceDate(today);
                attendanceModel.setEntryDate(hour);
                attendanceModel.setStatusExit("ENTRADA");
                attendanceModel.setStatus(entryValue(hour));
                attendanceRepository.save(attendanceModel);
            }else{
                attendanceToday.setDepartureDate(hour);
                attendanceToday.setStatusExit("SALIDA");
                attendanceRepository.save(attendanceToday);
            }
            return true;
        }catch(Exception e){
            log.error("Error al registrar asistencia para el DNI {}", dniEmployee, e);
            return false;
        }
    }

    public Optional<AttendanceModel> findByIdEmployee(int dniEmployee) {
        return attendanceRepository.findByEmployees_IdEmployee(dniEmployee);
    }

    public List<AttendanceModel> findAllRegisters(){
        return  attendanceRepository.findAll();
    }

    public int getCountAttendaceWell(){
        return attendanceRepository.countStatusWell();
    }

    public int getCountAttendaceMid(){
        return attendanceRepository.countStatusMid();
    }

    public int getCountAttendaceBad(){
        return attendanceRepository.countStatusBad();
    }

    public String entryValue(LocalDateTime horaIngreso) {
        LocalTime horaIngresada = horaIngreso.toLocalTime();

        if (horaIngresada.isBefore(HORA_ENTRADA)) {
            return "TEMPRANO";
        } else {
            return "TARDE";
        }
    }

    @Transactional
    public void procesarFaltasDelDia() {
        LocalDate hoy = LocalDate.now();

        List<EmployeesModel> empleados = employeesRepository.findAll();

        for (EmployeesModel emp : empleados) {

            boolean registroHoy = attendanceRepository
                    .findByEmployees_IdEmployeeAndAttendanceDate(emp.getIdEmployee(), hoy)
                    .isPresent();

            if (!registroHoy) {
                AttendanceModel falta = new AttendanceModel();
                falta.setEmployees(emp);
                falta.setAttendanceDate(hoy);
                falta.setStatus("FALTA");

                attendanceRepository.save(falta);
            }
        }
    }

    public long getCountAttendanceWellToday() {
        return attendanceRepository.countByStatusAndAttendanceDate("TEMPRANO", LocalDate.now());
    }

    public long getCountAttendanceMidToday() {
        return attendanceRepository.countByStatusAndAttendanceDate("TARDE", LocalDate.now());
    }

    public long getCountAttendanceBadToday() {
        return attendanceRepository.countByStatusAndAttendanceDate("FALTA", LocalDate.now());
    }

}
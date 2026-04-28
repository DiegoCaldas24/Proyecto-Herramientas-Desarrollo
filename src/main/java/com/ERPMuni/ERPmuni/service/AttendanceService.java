package com.ERPMuni.ERPmuni.service;

import com.ERPMuni.ERPmuni.model.AttendanceModel;
import com.ERPMuni.ERPmuni.model.EmployeesModel;
import com.ERPMuni.ERPmuni.repository.AttendanceRepository;
import com.ERPMuni.ERPmuni.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    public boolean saveAttendance(String dniEmployee) {
        try{
            AttendanceModel employee =  attendanceRepository.findByEmployees_Dni(dniEmployee).orElse(null);
            LocalDateTime hour = LocalDateTime.now();
            if(employee == null) {
                AttendanceModel attendanceModel = new AttendanceModel();
                attendanceModel.setEmployees(employeesRepository.findByDni(dniEmployee).get());
                attendanceModel.setEntryDate(hour);
                attendanceModel.setEntryDate(hour);
                attendanceModel.setStatusExit("ENTRADA");
                attendanceModel.setStatus(entryValue(hour));
                attendanceRepository.save(attendanceModel);
            }else{
                employee.setDepartureDate(hour);
                employee.setStatusExit("SALIDA");
                attendanceRepository.save(employee);
            }
            return true;
        }catch(Exception e){
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

    public void procesarFaltasDelDia() {
        LocalDateTime hoy = LocalDateTime.now();

        List<EmployeesModel> empleados = employeesRepository.findAll();

        for (EmployeesModel emp : empleados) {

            boolean registroHoy = attendanceRepository
                    .findByEmployees_IdEmployeeAndEntryDate(emp.getIdEmployee(), hoy)
                    .isPresent();

            if (!registroHoy) {
                AttendanceModel falta = new AttendanceModel();
                falta.setEmployees(emp);
                falta.setEntryDate(hoy);
                falta.setStatus("FALTA");

                attendanceRepository.save(falta);
            }
        }
    }

}
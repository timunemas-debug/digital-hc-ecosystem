package com.digitalhc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.digitalhc.model.Employee;
import com.digitalhc.model.EmployeeStatus;
import com.digitalhc.model.LeaveBalance;
import com.digitalhc.repository.EmployeeRepository;
import com.digitalhc.repository.LeaveBalanceRepository;

@Service
public class LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveBalanceService(LeaveBalanceRepository leaveBalanceRepository, EmployeeRepository employeeRepository){
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Scheduled(fixedRate = 3600000)
    public void addLeaveBalanceEmployee(){
        
        List<Employee> employees = employeeRepository.findByStatus(EmployeeStatus.AKTIF);

        for(Employee employee : employees){

            if (employee.getTanggalBergabungEmployee().plusYears(1).isAfter(LocalDate.now())) {
                continue;
            }

            if (employee.getLeaveBalancesList() != null) {
                continue;
            }

            LeaveBalance leaveBalance = new LeaveBalance();
            leaveBalance.setEmployee(employee);

            leaveBalanceRepository.save(leaveBalance);
        }
    }
}
package com.digitalhc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalhc.DTO.request.LeaveRequest;
import com.digitalhc.DTO.response.LeaveResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.LeaveMapper;
import com.digitalhc.model.Employee;
import com.digitalhc.model.Leave;
import com.digitalhc.model.LeaveStatus;
import com.digitalhc.model.Role;
import com.digitalhc.repository.EmployeeRepository;
import com.digitalhc.repository.LeaveRepository;

import jakarta.annotation.Resource;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final EmployeeRepository employeeRepository;

    public LeaveService(LeaveRepository leaveRepository, LeaveMapper leaveMapper, EmployeeRepository employeeRepository){
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public LeaveResponse addLeave(Long employeeId, LeaveRequest request){

        Employee employee = employeeRepository.findByEmployeeIdWithLock(employeeId)
                .orElseThrow(() -> new ResourceNotFound("Employee tidak ditemukan!"));
                
        if (employee.getTanggalBergabungEmployee() == null) {
            throw new BadRequestException("Tanggal bergabung belum tersedia!");
        }

        LocalDate tanggalBergabung = employee.getTanggalBergabungEmployee();
        long pendingLeave = leaveRepository.countByEmployeeAndStatus(employee, LeaveStatus.SUBMITTED);

        if(pendingLeave >= 2){
            throw new BadRequestException("Sedang menunggu persetujuan...");
        }

        if (LocalDate.now().isBefore(tanggalBergabung.plusYears(1))) {
            throw new BadRequestException("Karyawan belum 1 tahun bekerja!");
        }

        Leave leave = leaveMapper.toEntity(request);
        leave.setEmployee(employee);
        leave.setStatus(LeaveStatus.SUBMITTED);

        return leaveMapper.toResponse(leaveRepository.save(leave));
    }

    public List<LeaveResponse> getAllLeave(){
        return leaveRepository.findAll()
                .stream()
                .map(leaveMapper::toResponse)
                .toList();
    }

    public Leave getLeaveByLeaveId(Long leaveId){

        return leaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFound("Leave tidak ditemukan!"));
    }

    public List<LeaveResponse> getLeaveResponseByEmployeeId(Long employeeId){

        return leaveRepository.findByEmployeeEmployeeId(employeeId)
                .stream()
                .map(leaveMapper::toResponse)
                .toList();
    }

    public void viewRequestLeave(){

    }

    @Transactional
    public void processLeave(Long leaveId, LeaveStatus status){

        Leave leave = leaveRepository.findByLeaveIdWithLock(leaveId)
                .orElseThrow(() -> new ResourceNotFound("Leave dengan id tersebut tidak ditemukan!"));

        if (leave.getStatus() != LeaveStatus.SUBMITTED) {
            throw new BadRequestException("Status leave sudah di proses dan tidak dapat diubah!");
        }

        if (status != LeaveStatus.APPROVED && status != LeaveStatus.REJECTED) {
            throw new BadRequestException("Status hanya dapat menjadi Apprved atau Rejected");
        }

        if (leave.getApprovedBy() != null) {
            throw new BadRequestException("Leave sudah di proses");
        }

        leave.setApprovedBy(Role.ROLE_HC_MANAGER);
        leave.setStatus(status);

        leaveRepository.save(leave);
    }

    public void rejectLeave(){

    }
}
package com.digitalhc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.LeaveRequest;
import com.digitalhc.DTO.response.LeaveResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.LeaveMapper;
import com.digitalhc.model.Employee;
import com.digitalhc.model.Leave;
import com.digitalhc.model.LeaveStatus;
import com.digitalhc.repository.LeaveRepository;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;

    public LeaveService(LeaveRepository leaveRepository, LeaveMapper leaveMapper){
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
    }

    public LeaveResponse addLeave(Employee employee, LeaveRequest request){

        LocalDate tanggalBergabung = employee.getTanggalBergabungEmployee();
        long pendingLeave = leaveRepository.countByEmployeeAndStatus(employee, LeaveStatus.SUBMITTED);

        if (tanggalBergabung == null) {
            throw new BadRequestException("Tanggal bergabung belum tersedia!");
        }

        if(pendingLeave >= 2){
            throw new BadRequestException("Sedang menunggu persetujuan...");
        }

        if (LocalDate.now().isBefore(tanggalBergabung.plusYears(1))) {
            throw new BadRequestException("Karyawan belum 1 tahun bekerja!");
        }

        Leave leave = leaveMapper.toEntity(request);
        leave.setEmployee(employee);

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

    public LeaveResponse updateLeave(Long leaveId, LeaveStatus status, Employee employee){

        Leave leave = getLeaveByLeaveId(leaveId);

        if (leave.getStatus() != LeaveStatus.SUBMITTED) {
            throw new BadRequestException("Status leave sudah diproses dan tidak dapat diubah!");
        }

        if (leave.getApprovedBy() != null){
            throw new BadRequestException("Approved by sudah terisi");
        }
        
        leave.setApprovedBy(employee);
        leave.setStatus(status);

        return leaveMapper.toResponse(leaveRepository.save(leave));
    }
}
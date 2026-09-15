package com.digitalhc.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalhc.DTO.request.LeaveRequest;
import com.digitalhc.DTO.response.LeaveResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.LeaveMapper;
import com.digitalhc.model.Employee;
import com.digitalhc.model.EmployeeStatus;
import com.digitalhc.model.Leave;
import com.digitalhc.model.LeaveBalance;
import com.digitalhc.model.LeaveStatus;
import com.digitalhc.model.User;
import com.digitalhc.repository.EmployeeRepository;
import com.digitalhc.repository.LeaveBalanceRepository;
import com.digitalhc.repository.LeaveRepository;
import com.digitalhc.repository.UserRepository;
import com.digitalhc.security.SecurityService;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final EmployeeRepository employeeRepository;
    private final SecurityService securityService;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final UserRepository userRepository;

    public LeaveService(LeaveRepository leaveRepository, LeaveMapper leaveMapper, EmployeeRepository employeeRepository, SecurityService securityService, LeaveBalanceRepository leaveBalanceRepository, UserRepository userRepository){
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
        this.employeeRepository = employeeRepository;
        this.securityService = securityService;
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.userRepository = userRepository;
    }

    //UNTUK KARYAWAN MELAKUKAN PENGAJUAN CUTI
    @Transactional
    public LeaveResponse addLeave(LeaveRequest request){

        Long employeeId = securityService.getCurrentUserId();

        Employee employee = employeeRepository.findByEmployeeIdWithLock(employeeId)
                .orElseThrow(() -> new ResourceNotFound("Employee tidak ditemukan!"));

        Optional<LeaveBalance> leaveBalance = leaveBalanceRepository.findByEmployeeEmployeeId(employeeId);

        boolean hasActiveLeave = leaveRepository.existsByEmployeeEmployeeIdAndStatusAndStartDateLeaveLessThanEqualAndEndDateLeaveGreaterThanEqual(employeeId, LeaveStatus.SUBMITTED, request.getStartDateLeave(), request.getEndDateLeave());

        if (hasActiveLeave) {
            throw new BadRequestException("Pegawai sudah memiliki cuti ditanggal tersebut!");
        }

        if (employee.getStatus() != EmployeeStatus.AKTIF) {
            throw new BadRequestException("Employee tidak aktif!");
        }

        if (leaveBalance.isEmpty()) {
            throw new BadRequestException("Anda tidak memiliki leave balance!");
        }

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

    //UNTUK SETIAP MANAGER DAN JUGA ADMIN
    public List<LeaveResponse> getAllLeave(){
        return leaveRepository.findAll()
                .stream()
                .map(leaveMapper::toResponse)
                .toList();
    }

    //UNTUK SETIAP MANAGER DAN JUGA ADMIN
    public Leave getLeaveByLeaveId(Long leaveId){

        return leaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFound("Leave tidak ditemukan!"));
    }

    //UNTUK SETIAP MANAGER DAN JUGA ADMIN
    public List<LeaveResponse> getLeaveResponseByEmployeeId(Long employeeId){

        return leaveRepository.findByEmployeeEmployeeId(employeeId)
                .stream()
                .map(leaveMapper::toResponse)
                .toList();
    }

    //UNTUK SETIAP MANAGER YANG MELAKUKAN PROCESS LEAVE INI
    @Transactional
    public void processLeave(Long leaveId, LeaveStatus status){

        Leave leave = leaveRepository.findByLeaveIdWithLock(leaveId)
                .orElseThrow(() -> new ResourceNotFound("Leave dengan id tersebut tidak ditemukan!"));
                
        if (leave.getEmployee() == null) {
            throw new ResourceNotFound("Employee pemilik leave tidak ditemukan!");
        }

        Employee employee = leave.getEmployee();

        if (leave.getStatus() != LeaveStatus.SUBMITTED) {
            throw new BadRequestException("Status leave sudah diproses dan tidak dapat diubah!");
        }

        if (status != LeaveStatus.APPROVED && status != LeaveStatus.REJECTED) {
            throw new BadRequestException("Status hanya dapat menjadi Apprved atau Rejected");
        }
        
        if (employee.getStatus() != EmployeeStatus.AKTIF) {
            throw new BadRequestException("Employee sudah tidak aktif!");
        }

        if (leave.getAprovedBy() != null) {
            throw new BadRequestException("Leave sudah di proses");
        }

        if (status == LeaveStatus.APPROVED) {
            Long employeeId = employee.getEmployeeId();

            LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeEmployeeId(employeeId)
                    .orElseThrow(() -> new ResourceNotFound("Leave balance employee tidak ditemukan!"));

            int availableLeaves = leaveBalance.getTotalLeaves() - leaveBalance.getUsedLeaves();

            int totalDays = (int) ChronoUnit.DAYS.between(leave.getStartDateLeave(), leave.getEndDateLeave()) + 1;


            if (availableLeaves < totalDays) {
                throw new BadRequestException("Saldo leave tidak cukup!");
            }

            leaveBalance.setUsedLeaves(leaveBalance.getUsedLeaves() + totalDays);

            leaveBalanceRepository.save(leaveBalance);
        }

        Long userId = securityService.getCurrentUserId();

        User currentUser = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFound("User tidak ditemukan!"));

        leave.setAprovedBy(currentUser);
        leave.setStatus(status);

        leaveRepository.save(leave);
    }
}
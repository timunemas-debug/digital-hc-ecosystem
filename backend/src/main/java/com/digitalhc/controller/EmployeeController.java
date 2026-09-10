package com.digitalhc.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalhc.DTO.request.EmployeeRequest;
import com.digitalhc.DTO.request.UpdateEmployeeRequest;
import com.digitalhc.DTO.response.DashboardResponse;
import com.digitalhc.DTO.response.EmployeeResponse;
import com.digitalhc.DTO.response.UpdateEmployeeResponse;
import com.digitalhc.model.EmployeeStatus;
import com.digitalhc.service.EmployeeService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-employee")
    public EmployeeResponse addEmployee(@Valid @RequestBody EmployeeRequest request){
        return employeeService.addEmployee(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{employeeId}")
    public EmployeeResponse getEmployeeById(@PathVariable Long employeeId){
        return employeeService.getEmployeeResponseById(employeeId);
    }

    @GetMapping("/nama/{namaLengkapEmployee}")
    public EmployeeResponse getEmployeeByNama(@PathVariable String namaLengkapEmployee){
        return employeeService.getEmployeeByNama(namaLengkapEmployee);
    }

    @GetMapping("/tanggal/{tanggal}")
    public List<EmployeeResponse> getEmployeeByTanggalBergabung(@PathVariable LocalDate tanggal){
        return employeeService.getEmployeeByTanggalBergabungSetelah(tanggal);
    }

    @GetMapping("/tanggal-bergabung-between")
    public List<EmployeeResponse> getEmployeeBetween(@RequestParam LocalDate tanggal1, @RequestParam LocalDate tanggal2){
        return employeeService.getEmployeeByTanggalBergabungBetweeen(tanggal1, tanggal2);
    }

    @GetMapping("/{status}")
    public List<EmployeeResponse> getEmployeeByStatus(@PathVariable EmployeeStatus status){
        return employeeService.getEmployeeStatus(status);
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployeeById(@PathVariable Long employeeId){
        employeeService.deleteEmployeeById(employeeId);
    }

    @PatchMapping("/{employeeId}/update-employee")
    public UpdateEmployeeResponse updateProfileEmployee(@PathVariable Long employeeId, @Valid @RequestBody UpdateEmployeeRequest request){
        return employeeService.updateProfileEmployee(employeeId, request);
    }

    @PatchMapping("/{employeeId}/changePosition")
    public EmployeeResponse assignPosition(@PathVariable Long employeeId, @RequestParam Long positionId){
        return employeeService.assignPosition(employeeId, positionId);
    }

    @PatchMapping("/{employeeId}/changePosition")
    public EmployeeResponse changePosition(@PathVariable Long employeeId, @RequestParam Long positionId){
        return employeeService.changePosition(employeeId, positionId);
    }

    @GetMapping("/dashboard/{time}")
    public DashboardResponse dashboard(@PathVariable LocalDateTime time){
        return employeeService.dashboard(time);
    }
}
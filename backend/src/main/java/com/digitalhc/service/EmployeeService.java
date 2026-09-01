package com.digitalhc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.EmployeeRequest;
import com.digitalhc.DTO.request.UpdateEmployeeRequest;
import com.digitalhc.DTO.response.EmployeeResponse;
import com.digitalhc.DTO.response.UpdateEmployeeResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.EmployeeMapper;
import com.digitalhc.mapper.UpdateEmployeeMapper;
import com.digitalhc.model.Employee;
import com.digitalhc.model.EmployeeStatus;
import com.digitalhc.model.Position;
import com.digitalhc.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final UpdateEmployeeMapper updateEmployeeMapper;
    private final PositionService positionService;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, UpdateEmployeeMapper updateEmployeeMapper, PositionService positionService){
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.updateEmployeeMapper = updateEmployeeMapper;
        this.positionService = positionService;
    }

    public EmployeeResponse addEmployee(EmployeeRequest request){

        if(employeeRepository.existsByNamaLengkapEmployee(request.getNamaLengkapEmployee())){
            throw new IllegalArgumentException("Nama tersebut sudah digunakan!");
        }

        Employee employee = employeeMapper.toEntity(request);

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    public Employee getEmployeeById(Long employeeId){
        return employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFound("Employee dengan id tersebut tidak ada!"));
    }

    public EmployeeResponse getEmployeeResponseById(Long employeeId){

        Employee employee = getEmployeeById(employeeId);

        return employeeMapper.toResponse(employee);
    }

    public EmployeeResponse getEmployeeByNama(String namaLengkapEmployee){

        Employee employee = employeeRepository.findByNamaLengkapEmployee(namaLengkapEmployee)
                    .orElseThrow(() -> new ResourceNotFound("Nama tersebut tidak ada!"));
            
        return employeeMapper.toResponse(employee);
    }

    public List<EmployeeResponse> getEmployeeByTanggalBergabungSetelah(LocalDate tanggal){

        return employeeRepository.findByTanggalBergabungEmployee(tanggal)
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    public List<EmployeeResponse> getEmployeeByTanggalBergabungBetweeen(LocalDate tanggal1, LocalDate tanggal2){

        return employeeRepository.findByTanggalBergabungEmployeeBetween(tanggal1, tanggal2)
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    public List<EmployeeResponse> getEmployeeStatus(EmployeeStatus status){
        
        List<Employee> employees = employeeRepository.findByStatus(status);

        return employees.stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    public void deleteEmployeeById(Long employeeId){
        
        if(!employeeRepository.existsById(employeeId)){
            throw new ResourceNotFound("Employee tidak ditemukan!");
        }
        employeeRepository.deleteById(employeeId);
    }

    public UpdateEmployeeResponse updateProfileEmployee(Long employeeId, UpdateEmployeeRequest request){
        
        Employee employee = getEmployeeById(employeeId);

        employee.setNik(request.getNik());
        employee.setNamaLengkapEmployee(request.getNamaLengkapEmployee());
        employee.setNomerHpEmployee(request.getNomerHpEmployee());
        employee.setTanggalLahirEmployee(request.getTanggalLahirEmployee());

        employeeRepository.save(employee);

        return updateEmployeeMapper.mapToResponse(employee);
    }

    public EmployeeResponse assignPosition(Long employeeId, Long positionId){

        Employee employee = getEmployeeById(employeeId);

        if (employee.getPosition() != null) {
            throw new BadRequestException("Position sudah ditambahkan!");
        }

        Position position = positionService.getPositionById(positionId);

        employee.setPosition(position);

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    public EmployeeResponse changePosition(Long employeeId, Long positionId){

        Employee employee = getEmployeeById(employeeId);

        Position position = positionService.getPositionById(positionId);

        employee.setPosition(position);

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }
}
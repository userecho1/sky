package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);

    PageResult queryAllPage(EmployeePageQueryDTO employeePageQueryDTO);

    void enableOrDisable(Integer status, Long id);

    Employee queryById(Long id);

    void updateEmployee(EmployeeDTO employeeDTO);
}

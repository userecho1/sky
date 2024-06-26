package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags="员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation("员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation("员工退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }


    @ApiOperation("新增员工")
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);

        employeeService.save(employeeDTO);
        return Result.success();
    }

    @ApiOperation("员工分页查询")
    @GetMapping("page")
    public Result<PageResult> queryAllPage( EmployeePageQueryDTO employeePageQueryDTO){

        PageResult employees=employeeService.queryAllPage(employeePageQueryDTO);
        return Result.success(employees);
    }

 /*   启用禁用员工账号
    @param status
    @param id
    @return*/
    @ApiOperation("启用禁用员工账号")
    @PostMapping("status/{status}")
    public Result enableOrDisable( @PathVariable Integer status,
                                       Long id){

        employeeService.enableOrDisable(status,id);
        return Result.success();
    }

    @ApiOperation("根据id查询员工信息")
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){
        Employee employee=employeeService.queryById(id);
        return Result.success(employee);
    }

    @ApiOperation("编辑员工信息")
    @PutMapping
    private Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑员工信息{}", employeeDTO);
        employeeService.updateEmployee(employeeDTO);
        return Result.success();
    }
}

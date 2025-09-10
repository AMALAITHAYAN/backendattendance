package com.ontime.office.controller;

import com.ontime.office.dto.LoginDTO;
import com.ontime.office.dto.LoginResponseDTO;
import com.ontime.office.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://attendencefrontend.vercel.app"
})
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    // ✅ Authenticates a user and returns their profile details
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO loginDTO) {
        return employeeService.login(loginDTO);
    }
}

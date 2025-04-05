package com.fastForm.core.controllers;

import com.fastForm.core.Dto.ResponseDto;
import com.fastForm.core.Dto.UserDto;
import com.fastForm.core.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody UserDto userDTO) {
        return authService.register(userDTO);
    }

}

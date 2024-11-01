package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.AccountDTO;
import com.example.Makeup.dto.ApiResponse;
import com.example.Makeup.dto.LoginRequest;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.service.AccountService;
import com.example.Makeup.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static com.fasterxml.jackson.core.JsonFactory.builder;


@RestController
@RequestMapping("/api")
public class AuthApiController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> createAccount(@RequestBody AccountDTO account) {
        // Kiểm tra xem số điện thoại đã tồn tại hay chưa
        if (accountService.checkExists(account.getUserName())) {
            return ResponseEntity.badRequest().body("Username is existed!");
        }
        account.setPassWord(passwordEncoder.encode(account.getPassWord()));
        account.setRoleId(2);
        accountService.save(account);
        return ResponseEntity.ok("Create Success!");
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}

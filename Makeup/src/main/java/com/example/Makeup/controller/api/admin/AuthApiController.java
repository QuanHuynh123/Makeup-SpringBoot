package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.AccountDTO;
import com.example.Makeup.dto.ApiResponse;
import com.example.Makeup.dto.LoginRequest;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.security.AuthenticationSuccessHandler;
import com.example.Makeup.service.AccountService;
import com.example.Makeup.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

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

    @Autowired
    private AuthenticationProvider authenticationProvider ;


    @PostMapping("/registerUser")
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



    @PostMapping("/loginUser")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Login success : " + authentication.getName());
            System.out.println("Login success : " + authentication.getPrincipal());
            System.out.println("Login success : " + authentication.isAuthenticated());


            SecurityContextHolder.getContext().setAuthentication(SecurityContextHolder.getContext().getAuthentication());
            System.out.println("Set securityContextHolder  : "  + SecurityContextHolder.getContext().getAuthentication());

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            Map<String, String> response = Map.of(
                    "status", "Login success",
                    "redirectUrl", isAdmin ? "/admin" : "/home"
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> errorResponse = Map.of(
                    "status", "error",
                    "message", "Invalid credentials"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }


    @GetMapping("/status")
    public String getStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return "User is authenticated: " + authentication.getPrincipal() + " " + authentication.getName();
        } else {
            return "User is not authenticated";
        }
    }


}

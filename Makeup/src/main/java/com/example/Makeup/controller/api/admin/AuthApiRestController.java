package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.AccountDTO;
import com.example.Makeup.dto.LoginRequest;
import com.example.Makeup.dto.UserDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.service.AccountService;
import com.example.Makeup.service.RoleService;
import com.example.Makeup.service.UserService;
import jakarta.servlet.http.HttpSession;
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

import java.util.Map;


@RestController
@RequestMapping("/api")
public class AuthApiRestController {
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

    @Autowired
    UserService userService ;

    @PostMapping("/registerUser")
    public ResponseEntity<String> createAccount(@RequestBody AccountDTO account) {
        // Kiểm tra xem số điện thoại đã tồn tại hay chưa
        if (accountService.checkExists(account.getUserName())) {
            return ResponseEntity.badRequest().body("Username is existed!");
        }
        account.setPassWord(passwordEncoder.encode(account.getPassWord()));
        account.setRoleId(2);
        Account accountEntity =  accountService.save(account);
        userService.createInforUser(account,accountEntity);
        return ResponseEntity.ok("Create Success!");
    }



    @PostMapping("/loginUser")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest , HttpSession session) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            Authentication authenticationSuccess = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("SecurityContextHolder : " +  authenticationSuccess);

            // Dung` httpsession luu tam
            UserDTO userDTO = userService.getInforUser(loginRequest.getUsername());
            session.setAttribute("user", userDTO);

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            Map<String, String> response = Map.of(
                    "status", "Đăng nhập thành công!",
                    "redirectUrl", isAdmin ? "/admin" : "/home"
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> errorResponse = Map.of(
                    "status", "error",
                    "message", "Đăng nhập thất bại!"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }


    @GetMapping("/status")
    public String getStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println( "User is authenticated: " + authentication.getPrincipal() + " " + authentication.getName());
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println( "User is authenticated: " + authentication.getPrincipal() + " " + authentication.getName());
            return "User is authenticated" ;
        } else {
            return "User is not authenticated" ;
        }
    }

}

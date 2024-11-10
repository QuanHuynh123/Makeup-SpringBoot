package com.example.Makeup.controller.api;/*
package com.example.Makeup.controller.api;

import com.example.Makeup.entity.Account;
import com.example.Makeup.repository.RoleRepository;
import com.example.Makeup.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register/user")
    public Account createAccount(@RequestBody Account account){
        account.setPassWord(passwordEncoder.encode(account.getPassWord()));
        return accountService.save(account);
    }
}
*/

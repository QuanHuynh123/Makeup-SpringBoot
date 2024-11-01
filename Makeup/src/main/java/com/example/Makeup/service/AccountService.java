package com.example.Makeup.service;

import com.example.Makeup.dto.AccountDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.Role;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.AccountMapper;
import com.example.Makeup.repository.AccountRepository;
import com.example.Makeup.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account =  accountRepository.findByUserName(username);
        if (account.isPresent()){
            var userObj = account.get();
            return  org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUserName())
                    .password(userObj.getPassWord())
                    .roles(getRoles(userObj))
                    .build();
        }else {
            throw new UsernameNotFoundException(username);
        }
    }

    public String[] getRoles(Account account){
        if (account.getRole() == null) {
            return new String[]{"USER"};
        }
        return account.getRole().getNameRole().split(",");
    }

    public void save(AccountDTO account){
        Role role = roleRepository.findById(account.getRoleId())
                .orElseThrow(()-> new AppException(ErrorCode.CANT_FOUND));
        Account saveAccount = accountMapper.toEntity(account);
        saveAccount.setRole(role);
        accountRepository.save(saveAccount);
    }

    public boolean checkExists(String userName){
        return accountRepository.existsByUserName(userName);
    }
}

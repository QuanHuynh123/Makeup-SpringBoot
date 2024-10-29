package com.example.Makeup.service;

import com.example.Makeup.entity.Account;
import com.example.Makeup.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

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

    public Account save(Account account){
        return accountRepository.save(account);
    }
}

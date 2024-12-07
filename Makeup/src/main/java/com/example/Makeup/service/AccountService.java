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
import org.springframework.stereotype.Service;

import java.util.List;
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
        return (account.getRole() != null && account.getRole().getNameRole() != null)
                ? account.getRole().getNameRole().split(",")
                : new String[]{"USER"};
    }


    public Account save(AccountDTO account){
        Role role = roleRepository.findById(account.getRoleId())
                .orElseThrow(()-> new AppException(ErrorCode.CANT_FOUND));
        Account saveAccount = accountMapper.toEntity(account);
        saveAccount.setRole(role);
        return accountRepository.save(saveAccount);
    }

    public boolean checkExists(String userName){
        return accountRepository.existsByUserName(userName);
    }

    // Lấy tất cả tài khoản
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
    // Tìm tài khoản theo ID
    public Optional<Account> findById(int id) {
        return accountRepository.findById(id);
    }
    // Xóa tài khoản
    public boolean delete(int id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }
    // Cập nhật tài khoản
    public Account update(AccountDTO accountDTO, int id) {
        Optional<Account> existingAccountOpt = accountRepository.findById(id);
        if (existingAccountOpt.isPresent()) {
            Account existingAccount = existingAccountOpt.get();
            Account updatedAccount = accountMapper.toEntity(accountDTO);
            // Chỉ cập nhật những trường cần thiết
            existingAccount.setUserName(updatedAccount.getUserName());
            existingAccount.setPassWord(updatedAccount.getPassWord());
            Role role = roleRepository.findById(accountDTO.getRoleId())
                    .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));
            existingAccount.setRole(role);
            return accountRepository.save(existingAccount);
        } else {
            throw new AppException(ErrorCode.CANT_FOUND);
        }
    }

    public boolean isUsernameExists(String username) {
        Optional<Account> account = accountRepository.findByUserNameIgnoreCase(username);
        return account.isPresent();
    }
}

package com.example.Makeup.service;

import com.example.Makeup.dto.AccountDTO;
import com.example.Makeup.dto.UserDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.Cart;
import com.example.Makeup.entity.Role;
import com.example.Makeup.entity.User;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.AccountMapper;
import com.example.Makeup.mapper.UserMapper;
import com.example.Makeup.repository.AccountRepository;
import com.example.Makeup.repository.RoleRepository;
import com.example.Makeup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    UserMapper userMapper ;
    public void createInforUser(AccountDTO account, Account accountEntity){
        User user = new User();
        user.setPhone(account.getUserName());

        user.setAccount(accountEntity);
        userRepository.save(user);
    }

    public UserDTO getInforUser(String phone){
        User user =  userRepository.findByPhone(phone);
        return userMapper.toUserDTO(user);
    }
}

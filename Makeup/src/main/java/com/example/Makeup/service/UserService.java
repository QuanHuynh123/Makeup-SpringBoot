package com.example.Makeup.service;

import com.example.Makeup.dto.AccountDTO;
import com.example.Makeup.dto.UserDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.User;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.AccountMapper;
import com.example.Makeup.mapper.UserMapper;
import com.example.Makeup.repository.RoleRepository;
import com.example.Makeup.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        System.out.println(phone);
        User user =  userRepository.findByPhoneAndAccountNotNull(phone);
        return userMapper.toUserDTO(user);
    }

    public User getUserById(int userId){
       return  userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public User findUserByAccountId(int accountId){
        return userRepository.findByAccountId(accountId);
    }

    @Transactional
    public UserDTO updateInforUser(String phone , String name , String email , String address  ) {
        User user = userRepository.findByPhoneAndAccountNotNull(phone);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        user.setAddress(address);
        user.setEmail(email);
        user.setFullName(name);
        User userUpdateSuccess = userRepository.save(user);
        return userMapper.toUserDTO(userUpdateSuccess);
    }

    public UserDTO createUser(UserDTO userDTO) {
        // Kiểm tra nếu user đã tồn tại
        var existingUser = userRepository.findByEmailAndPhone(userDTO.getEmail(), userDTO.getPhone());
        if (existingUser.isPresent()) {
            // Chuyển User entity thành DTO
            User user = existingUser.get();
            return new UserDTO(user.getId(), user.getFullName(), user.getEmail(), user.getAddress(), user.getPhone(),
                    0, // role (bạn có thể cập nhật logic phù hợp)
                    user.getCart() != null ? user.getCart().getId() : 0,
                    user.getAccount() != null ? user.getAccount().getId() : 0); // Kiểm tra account != null
        }
        // Tạo user mới
        User newUser = new User();
        newUser.setFullName(userDTO.getFullName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setAddress(userDTO.getAddress());
        newUser.setPhone(userDTO.getPhone());
        // Lưu user vào database
        User savedUser = userRepository.save(newUser);
        // Trả về thông tin người dùng vừa được lưu
        return new UserDTO(savedUser.getId(), savedUser.getFullName(), savedUser.getEmail(), savedUser.getAddress(),
                savedUser.getPhone(), 0,
                savedUser.getCart() != null ? savedUser.getCart().getId() : 0,
                savedUser.getAccount() != null ? savedUser.getAccount().getId() : 0); // Kiểm tra account != null
    }

}

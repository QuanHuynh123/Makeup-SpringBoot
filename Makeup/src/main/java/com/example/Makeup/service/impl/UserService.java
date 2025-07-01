package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.entity.User;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.UserMapper;
import com.example.Makeup.repository.UserRepository;
import com.example.Makeup.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ApiResponse<UserDTO> getUserDetailByUserName(String userName){
        User user =  userRepository.findByAccount_userName(userName)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        return ApiResponse.<UserDTO>builder()
                .code(200)
                .message("Get user success")
                .result(userMapper.toUserDTO(user))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<UserDTO> updateUser(UUID accountId , String name , String email , String address  ) {
        User user = userRepository.findByAccount_Id(accountId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        user.setAddress(address);
        user.setEmail(email);
        user.setFullName(name);
        User userUpdateSuccess = userRepository.save(user);
        return ApiResponse.<UserDTO>builder()
                .code(200)
                .message("Get user success")
                .result(userMapper.toUserDTO(userUpdateSuccess))
                .build();
    }

    @Override
    public ApiResponse<UserDTO> createUser(UserDTO userDTO) {

        User newUser = new User();
        newUser.setFullName(userDTO.getFullName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setAddress(userDTO.getAddress());
        newUser.setPhone(userDTO.getPhone());
        newUser.setGuest(true);
        newUser.setGuestToken(UUID.randomUUID().toString());

        User savedUser = userRepository.save(newUser);

        return ApiResponse.<UserDTO>builder()
                .code(200)
                .message("Create user success")
                .result(userMapper.toUserDTO(savedUser))
                .build();
    }

}

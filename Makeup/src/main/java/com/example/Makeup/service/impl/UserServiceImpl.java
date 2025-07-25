package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.request.UpdateProfileUserRequest;
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
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ApiResponse<UserDTO> getUserDetailByUserName(String userName){
        User user =  userRepository.findByAccount_userName(userName)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        return ApiResponse.success("Get user detail success", userMapper.toUserDTO(user));
    }

    @Override
    @Transactional
    public ApiResponse<UserDTO> updateUser(UpdateProfileUserRequest profileUserRequest, UUID accountId) {
        User user = userRepository.findByAccount_Id(accountId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        user.setAddress(profileUserRequest.getAddress());
        user.setEmail(profileUserRequest.getEmail());
        user.setFullName(profileUserRequest.getName());
        user.setPhone(profileUserRequest.getPhone());
        User userUpdateSuccess = userRepository.save(user);
        return ApiResponse.success("Update user success", userMapper.toUserDTO(userUpdateSuccess));
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

        return ApiResponse.success("Create user success",userMapper.toUserDTO(savedUser));
    }

    @Override
    public UserDTO loadUserDTOByUsername(String username) {
        return userRepository.findByAccount_userName(username)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserDTO loadUserDTOById(UUID userId) {
        return userRepository.findById(userId)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}

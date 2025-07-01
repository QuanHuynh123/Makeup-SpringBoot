package com.example.Makeup.service;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.response.common.ApiResponse;

import java.util.UUID;

public interface IUserService {

    ApiResponse<UserDTO> getUserDetailByUserName(String userName);
    ApiResponse<UserDTO> updateUser(UUID accountId, String name, String email, String address);
    ApiResponse<UserDTO> createUser(UserDTO userDTO);
}

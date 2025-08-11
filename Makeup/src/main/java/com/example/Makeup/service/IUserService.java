package com.example.Makeup.service;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.request.UpdateProfileUserRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.util.UUID;

public interface IUserService {

  ApiResponse<UserDTO> getUserDetailByUserName(String userName);

  ApiResponse<UserDTO> updateUser(UpdateProfileUserRequest profileUserRequest, UUID accountId);

  ApiResponse<UserDTO> createUser(UserDTO userDTO);

  UserDTO loadUserDTOByUsername(String username);

  UserDTO loadUserDTOById(UUID userId);

  ApiResponse<UserDTO> getUserById(UUID userId);
}

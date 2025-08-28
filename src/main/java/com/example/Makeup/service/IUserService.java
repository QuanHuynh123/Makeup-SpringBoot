package com.example.Makeup.service;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.request.UpdateProfileUserRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.util.UUID;

public interface IUserService {

  UserDTO getUserDetailByUserName(String userName);

  UserDTO updateUser(UpdateProfileUserRequest profileUserRequest, UUID accountId);

  UserDTO createUser(UserDTO userDTO);

  UserDTO loadUserDTOByUsername(String username);

  UserDTO loadUserDTOById(UUID userId);

  ApiResponse<UserDTO> getUserById(UUID userId);
}

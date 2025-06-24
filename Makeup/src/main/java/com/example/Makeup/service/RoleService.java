package com.example.Makeup.service;

import com.example.Makeup.entity.Role;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public ApiResponse<List<Role>> getListRole() {
        List<Role> roleList = roleRepository.findAll();
        if (roleList.isEmpty()) {
            throw new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND);
        }
        return ApiResponse.<List<Role>>builder()
                .code(200)
                .message("List of roles")
                .result(roleList)
                .build();
    }
}

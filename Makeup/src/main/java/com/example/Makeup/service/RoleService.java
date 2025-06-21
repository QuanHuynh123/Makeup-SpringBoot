package com.example.Makeup.service;

import com.example.Makeup.entity.Role;
import com.example.Makeup.enums.ApiResponse;
import com.example.Makeup.enums.AppException;
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
            throw new AppException(ErrorCode.IS_EMPTY);
        }
        return ApiResponse.<List<Role>>builder()
                .code(200)
                .message("List of roles")
                .result(roleList)
                .build();
    }
}

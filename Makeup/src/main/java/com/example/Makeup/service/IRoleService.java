package com.example.Makeup.service;

import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.Role;
import java.util.List;

public interface IRoleService {

  ApiResponse<List<Role>> getListRole();
}

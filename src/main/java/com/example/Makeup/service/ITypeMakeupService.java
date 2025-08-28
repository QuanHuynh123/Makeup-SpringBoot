package com.example.Makeup.service;

import com.example.Makeup.dto.model.TypeMakeupDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.util.List;

public interface ITypeMakeupService {

  List<TypeMakeupDTO> getAllTypeMakeup();
}

package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.TypeMakeupDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.TypeMakeup;
import com.example.Makeup.mapper.TypeMakeupMapper;
import com.example.Makeup.repository.TypeMakeupRepository;
import com.example.Makeup.service.ITypeMakeupService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeMakeupServiceImpl implements ITypeMakeupService {

  private final TypeMakeupRepository typeMakeupRepository;
  private final TypeMakeupMapper typeMakeupMapper;

  @Override
  public List<TypeMakeupDTO> getAllTypeMakeup() {
    List<TypeMakeup> typeMakeups = typeMakeupRepository.findAll();
    return typeMakeups.stream().map(typeMakeupMapper::toTypeMakeupDTO).collect(Collectors.toList());
  }
}

package com.example.Makeup.service;

import com.example.Makeup.entity.ServiceMakeup;
import com.example.Makeup.repository.ServiceMakeupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceMakeupService {

    @Autowired
    private ServiceMakeupRepository serviceMakeupRepository;

    // Phương thức lấy tất cả các dịch vụ Makeup
    public List<ServiceMakeup> getAllServices() {
        return serviceMakeupRepository.findAll();
    }
}

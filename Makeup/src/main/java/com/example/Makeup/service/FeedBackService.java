package com.example.Makeup.service;

import com.example.Makeup.repository.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedBackService {

    @Autowired
    FeedBackRepository feedBackRepository;

}

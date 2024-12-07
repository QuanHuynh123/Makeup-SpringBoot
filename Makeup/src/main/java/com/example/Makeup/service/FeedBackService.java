package com.example.Makeup.service;

import com.example.Makeup.dto.FeedBackDTO;
import com.example.Makeup.entity.FeedBack;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.FeedbackMapper;
import com.example.Makeup.repository.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackService {

    @Autowired
    FeedBackRepository feedBackRepository;
    @Autowired
    FeedbackMapper feedbackMapper;
    public List<FeedBackDTO> getAllFeedBack (){
        List<FeedBack> feedBacks = feedBackRepository.findAll();
        if (feedBacks.isEmpty()) throw  new AppException(ErrorCode.CANT_FOUND);
        return feedBacks.stream()
                .map(feedbackMapper::toFeedBackDTO)
                .collect(Collectors.toList());
    }

    public List<FeedBackDTO> getGoodFeedback(int minRating){
        List<FeedBack> feedBacks =  feedBackRepository.findByRatingGreaterThanEqual(minRating);
//        if (feedBacks.isEmpty())
//            throw  new AppException(ErrorCode.CANT_FOUND);

        return feedBacks.stream()
                .map(feedbackMapper::toFeedBackDTO)
                .collect(Collectors.toList());
    }

}
    
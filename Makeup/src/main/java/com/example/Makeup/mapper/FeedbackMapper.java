package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.entity.FeedBack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")

public interface FeedbackMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "typeMakeup.id", target = "typeMakeupId")
    FeedBackDTO toFeedBackDTO(FeedBack feedBack);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "typeMakeupId", target = "typeMakeup.id")
    FeedBack toFeedBackEntity(FeedBackDTO feedBackDTO);
}

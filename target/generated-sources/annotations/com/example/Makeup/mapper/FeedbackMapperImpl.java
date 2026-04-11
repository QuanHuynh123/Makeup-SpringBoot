package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.entity.FeedBack;
import com.example.Makeup.entity.TypeMakeup;
import com.example.Makeup.entity.User;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-10T20:55:31+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class FeedbackMapperImpl implements FeedbackMapper {

    @Override
    public FeedBackDTO toFeedBackDTO(FeedBack feedBack) {
        if ( feedBack == null ) {
            return null;
        }

        UUID userId = null;
        int typeMakeupId = 0;
        String comment = null;
        LocalDateTime createdAt = null;
        int rating = 0;
        LocalDateTime updatedAt = null;
        UUID id = null;

        userId = feedBackUserId( feedBack );
        typeMakeupId = feedBackTypeMakeupId( feedBack );
        comment = feedBack.getComment();
        createdAt = feedBack.getCreatedAt();
        rating = feedBack.getRating();
        updatedAt = feedBack.getUpdatedAt();
        id = feedBack.getId();

        FeedBackDTO feedBackDTO = new FeedBackDTO( id, rating, comment, userId, typeMakeupId, createdAt, updatedAt );

        return feedBackDTO;
    }

    @Override
    public FeedBack toFeedBackEntity(FeedBackDTO feedBackDTO) {
        if ( feedBackDTO == null ) {
            return null;
        }

        FeedBack feedBack = new FeedBack();

        feedBack.setUser( feedBackDTOToUser( feedBackDTO ) );
        feedBack.setTypeMakeup( feedBackDTOToTypeMakeup( feedBackDTO ) );
        feedBack.setCreatedAt( feedBackDTO.getCreatedAt() );
        feedBack.setUpdatedAt( feedBackDTO.getUpdatedAt() );
        feedBack.setComment( feedBackDTO.getComment() );
        feedBack.setId( feedBackDTO.getId() );
        feedBack.setRating( feedBackDTO.getRating() );

        return feedBack;
    }

    private UUID feedBackUserId(FeedBack feedBack) {
        if ( feedBack == null ) {
            return null;
        }
        User user = feedBack.getUser();
        if ( user == null ) {
            return null;
        }
        UUID id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private int feedBackTypeMakeupId(FeedBack feedBack) {
        if ( feedBack == null ) {
            return 0;
        }
        TypeMakeup typeMakeup = feedBack.getTypeMakeup();
        if ( typeMakeup == null ) {
            return 0;
        }
        int id = typeMakeup.getId();
        return id;
    }

    protected User feedBackDTOToUser(FeedBackDTO feedBackDTO) {
        if ( feedBackDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( feedBackDTO.getUserId() );

        return user;
    }

    protected TypeMakeup feedBackDTOToTypeMakeup(FeedBackDTO feedBackDTO) {
        if ( feedBackDTO == null ) {
            return null;
        }

        TypeMakeup typeMakeup = new TypeMakeup();

        typeMakeup.setId( feedBackDTO.getTypeMakeupId() );

        return typeMakeup;
    }
}

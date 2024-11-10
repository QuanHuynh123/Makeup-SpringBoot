package com.example.Makeup.mapper;

import com.example.Makeup.dto.FeedBackDTO;
import com.example.Makeup.entity.FeedBack;
import com.example.Makeup.entity.ServiceMakeup;
import com.example.Makeup.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T16:42:42+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class FeedbackMapperImpl implements FeedbackMapper {

    @Override
    public FeedBackDTO toFeedBackDTO(FeedBack feedBack) {
        if ( feedBack == null ) {
            return null;
        }

        FeedBackDTO feedBackDTO = new FeedBackDTO();

        feedBackDTO.setUserId( feedBackUserId( feedBack ) );
        feedBackDTO.setServiceMakeupId( feedBackServiceMakeupId( feedBack ) );
        feedBackDTO.setId( feedBack.getId() );
        feedBackDTO.setRating( feedBack.getRating() );
        feedBackDTO.setComment( feedBack.getComment() );
        feedBackDTO.setReviewDate( feedBack.getReviewDate() );

        return feedBackDTO;
    }

    @Override
    public FeedBack toFeedBackEntity(FeedBackDTO feedBackDTO) {
        if ( feedBackDTO == null ) {
            return null;
        }

        FeedBack feedBack = new FeedBack();

        feedBack.setUser( feedBackDTOToUser( feedBackDTO ) );
        feedBack.setServiceMakeup( feedBackDTOToServiceMakeup( feedBackDTO ) );
        feedBack.setId( feedBackDTO.getId() );
        feedBack.setRating( feedBackDTO.getRating() );
        feedBack.setComment( feedBackDTO.getComment() );
        feedBack.setReviewDate( feedBackDTO.getReviewDate() );

        return feedBack;
    }

    private int feedBackUserId(FeedBack feedBack) {
        if ( feedBack == null ) {
            return 0;
        }
        User user = feedBack.getUser();
        if ( user == null ) {
            return 0;
        }
        int id = user.getId();
        return id;
    }

    private int feedBackServiceMakeupId(FeedBack feedBack) {
        if ( feedBack == null ) {
            return 0;
        }
        ServiceMakeup serviceMakeup = feedBack.getServiceMakeup();
        if ( serviceMakeup == null ) {
            return 0;
        }
        int id = serviceMakeup.getId();
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

    protected ServiceMakeup feedBackDTOToServiceMakeup(FeedBackDTO feedBackDTO) {
        if ( feedBackDTO == null ) {
            return null;
        }

        ServiceMakeup serviceMakeup = new ServiceMakeup();

        serviceMakeup.setId( feedBackDTO.getServiceMakeupId() );

        return serviceMakeup;
    }
}

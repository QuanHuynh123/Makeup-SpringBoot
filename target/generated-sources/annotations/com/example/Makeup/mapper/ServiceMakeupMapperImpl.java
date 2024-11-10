package com.example.Makeup.mapper;

import com.example.Makeup.dto.ServiceMakeupDTO;
import com.example.Makeup.entity.ServiceMakeup;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T16:42:42+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ServiceMakeupMapperImpl implements ServiceMakeupMapper {

    @Override
    public ServiceMakeupDTO toServiceMakeupDTO(ServiceMakeup serviceMakeup) {
        if ( serviceMakeup == null ) {
            return null;
        }

        ServiceMakeupDTO serviceMakeupDTO = new ServiceMakeupDTO();

        serviceMakeupDTO.setId( serviceMakeup.getId() );
        serviceMakeupDTO.setNameService( serviceMakeup.getNameService() );
        serviceMakeupDTO.setDescription( serviceMakeup.getDescription() );
        serviceMakeupDTO.setPrice( serviceMakeup.getPrice() );

        return serviceMakeupDTO;
    }

    @Override
    public ServiceMakeup toServiceMakeupEntity(ServiceMakeupDTO serviceMakeupDTO) {
        if ( serviceMakeupDTO == null ) {
            return null;
        }

        ServiceMakeup serviceMakeup = new ServiceMakeup();

        serviceMakeup.setId( serviceMakeupDTO.getId() );
        serviceMakeup.setNameService( serviceMakeupDTO.getNameService() );
        serviceMakeup.setDescription( serviceMakeupDTO.getDescription() );
        serviceMakeup.setPrice( serviceMakeupDTO.getPrice() );

        return serviceMakeup;
    }
}

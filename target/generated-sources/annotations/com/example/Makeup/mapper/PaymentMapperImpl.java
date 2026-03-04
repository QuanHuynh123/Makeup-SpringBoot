package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.PaymentDTO;
import com.example.Makeup.entity.Payment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-20T22:10:21+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentDTO toPaymentDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setId( payment.getId() );
        paymentDTO.setNamePaymentMethod( payment.getNamePaymentMethod() );
        paymentDTO.setStatus( payment.isStatus() );
        paymentDTO.setCreatedAt( payment.getCreatedAt() );
        paymentDTO.setUpdatedAt( payment.getUpdatedAt() );

        return paymentDTO;
    }

    @Override
    public Payment toPaymentEntity(PaymentDTO paymentDTO) {
        if ( paymentDTO == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setCreatedAt( paymentDTO.getCreatedAt() );
        payment.setUpdatedAt( paymentDTO.getUpdatedAt() );
        payment.setId( paymentDTO.getId() );
        payment.setNamePaymentMethod( paymentDTO.getNamePaymentMethod() );
        payment.setStatus( paymentDTO.isStatus() );

        return payment;
    }
}

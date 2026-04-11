package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.PaymentDTO;
import com.example.Makeup.entity.Payment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-10T20:55:31+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentDTO toPaymentDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setCreatedAt( payment.getCreatedAt() );
        paymentDTO.setId( payment.getId() );
        paymentDTO.setNamePaymentMethod( payment.getNamePaymentMethod() );
        paymentDTO.setStatus( payment.isStatus() );
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

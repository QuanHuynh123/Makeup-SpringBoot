package com.example.Makeup.mapper;

import com.example.Makeup.dto.PaymentDTO;
import com.example.Makeup.entity.Payment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T18:08:01+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
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

        return paymentDTO;
    }

    @Override
    public Payment toPaymentEntity(PaymentDTO paymentDTO) {
        if ( paymentDTO == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setId( paymentDTO.getId() );
        payment.setNamePaymentMethod( paymentDTO.getNamePaymentMethod() );
        payment.setStatus( paymentDTO.isStatus() );

        return payment;
    }
}

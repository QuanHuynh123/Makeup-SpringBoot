package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.PaymentDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toPaymentDTO(Payment payment);

    Payment toPaymentEntity(PaymentDTO paymentDTO);
}

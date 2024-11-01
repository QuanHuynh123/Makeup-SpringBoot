package com.example.Makeup.mapper;

import com.example.Makeup.dto.*;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(source = "order.id", target = "orderId")
    PaymentDTO toPaymentDTO(Payment payment);

    @Mapping(source = "orderId", target = "order.id")
    Payment toPaymentEntity(PaymentDTO paymentDTO);
}

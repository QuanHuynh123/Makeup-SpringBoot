package com.example.Makeup.service;

import com.example.Makeup.dto.CartItemDTO;
import com.example.Makeup.entity.CartItem;
import com.example.Makeup.entity.Order;
import com.example.Makeup.entity.OrderItem;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.repository.CartItemRepository;
import com.example.Makeup.repository.OrderItemRepository;
import com.example.Makeup.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    CartItemService cartItemService;
    public boolean createOrderItem(int cartId, int orderId){
        List<CartItem> cartItemList = cartItemRepository.findAllByCartId(cartId);
        if (cartItemList.isEmpty()){
            throw new AppException(ErrorCode.CANT_FOUND);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new AppException(ErrorCode.ORDER_NOT_FOUND));

        for (CartItem cartItem : cartItemList){
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUseDate(cartItem.getUseDate());
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        }

        cartItemService.deleteAllCartItem(cartId);
        return  true;
    }
}

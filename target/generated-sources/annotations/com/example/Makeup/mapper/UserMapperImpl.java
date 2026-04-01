package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.User;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-01T21:04:50+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UUID accountId = null;
        String address = null;
        LocalDateTime createdAt = null;
        String email = null;
        String fullName = null;
        String phone = null;
        LocalDateTime updatedAt = null;
        UUID id = null;

        accountId = userAccountId( user );
        address = user.getAddress();
        createdAt = user.getCreatedAt();
        email = user.getEmail();
        fullName = user.getFullName();
        phone = user.getPhone();
        updatedAt = user.getUpdatedAt();
        id = user.getId();

        Integer role = null;
        UUID cartId = null;

        UserDTO userDTO = new UserDTO( id, fullName, email, address, phone, role, cartId, accountId, createdAt, updatedAt );

        return userDTO;
    }

    @Override
    public User toUserEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setCreatedAt( userDTO.getCreatedAt() );
        user.setUpdatedAt( userDTO.getUpdatedAt() );
        user.setAddress( userDTO.getAddress() );
        user.setEmail( userDTO.getEmail() );
        user.setFullName( userDTO.getFullName() );
        user.setId( userDTO.getId() );
        user.setPhone( userDTO.getPhone() );

        return user;
    }

    private UUID userAccountId(User user) {
        if ( user == null ) {
            return null;
        }
        Account account = user.getAccount();
        if ( account == null ) {
            return null;
        }
        UUID id = account.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

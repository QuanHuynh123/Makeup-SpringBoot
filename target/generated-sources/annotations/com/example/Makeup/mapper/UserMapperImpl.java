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
    date = "2026-03-19T14:14:35+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UUID accountId = null;
        String fullName = null;
        String email = null;
        String address = null;
        String phone = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        UUID id = null;

        accountId = userAccountId( user );
        fullName = user.getFullName();
        email = user.getEmail();
        address = user.getAddress();
        phone = user.getPhone();
        createdAt = user.getCreatedAt();
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
        user.setId( userDTO.getId() );
        user.setFullName( userDTO.getFullName() );
        user.setEmail( userDTO.getEmail() );
        user.setAddress( userDTO.getAddress() );
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

package com.example.Makeup.mapper;

import com.example.Makeup.dto.AccountDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T18:08:01+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDTO toDTO(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setRoleId( accountRoleId( account ) );
        accountDTO.setId( account.getId() );
        accountDTO.setUserName( account.getUserName() );
        accountDTO.setPassWord( account.getPassWord() );

        return accountDTO;
    }

    @Override
    public Account toEntity(AccountDTO account) {
        if ( account == null ) {
            return null;
        }

        Account account1 = new Account();

        account1.setRole( accountDTOToRole( account ) );
        account1.setId( account.getId() );
        account1.setUserName( account.getUserName() );
        account1.setPassWord( account.getPassWord() );

        return account1;
    }

    private int accountRoleId(Account account) {
        if ( account == null ) {
            return 0;
        }
        Role role = account.getRole();
        if ( role == null ) {
            return 0;
        }
        int id = role.getId();
        return id;
    }

    protected Role accountDTOToRole(AccountDTO accountDTO) {
        if ( accountDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( accountDTO.getRoleId() );

        return role;
    }
}

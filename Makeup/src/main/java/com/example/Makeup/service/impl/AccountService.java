package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.AccountDTO;
import com.example.Makeup.dto.request.RegisterRequest;
import com.example.Makeup.dto.request.UpdateAccountRequest;
import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.RefreshToken;
import com.example.Makeup.entity.Role;
import com.example.Makeup.entity.User;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.AccountMapper;
import com.example.Makeup.repository.AccountRepository;
import com.example.Makeup.repository.RefreshTokenRepository;
import com.example.Makeup.repository.RoleRepository;
import com.example.Makeup.repository.UserRepository;
import com.example.Makeup.security.JWTProvider;
import com.example.Makeup.service.IAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService, IAccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountMapper accountMapper;
    private final JWTProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh.expiration}")
    private long expiredRefreshToken ;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account =  accountRepository.findByUserName(username);
        if (account.isPresent()){
            var userObj = account.get();
            return  org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUserName())
                    .password(userObj.getPassWord())
                    .roles(getRoles(userObj))
                    .build();
        }else {
            throw new UsernameNotFoundException(username);
        }
    }

    public String[] getRoles(Account account){
        return (account.getRole() != null && account.getRole().getNameRole() != null)
                ? account.getRole().getNameRole().split(",")
                : new String[]{"USER"};
    }

    @Override
    @Transactional
    public ApiResponse<String> authenticate(String username, String password) {

        Account account = accountRepository.findByUserName(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(password, account.getPassWord())) {
            throw new AppException(ErrorCode.USER_PASSWORD_NOT_MATCH);
        }

        log.info("Pass authentication for user: {}", username);
        log.debug("Checking existing refresh token for user: {}", username);


        RefreshToken refreshToken;

        Optional<RefreshToken> optionalToken = refreshTokenRepository
                .findLatestValidTokenByAccount(account.getId());

        if (optionalToken.isPresent() && optionalToken.get().getExpiryDate().isAfter(LocalDateTime.now())) {
            // Using existing valid token
            log.info("Using existing valid refresh token for user: {}", username);
            refreshToken = optionalToken.get();
        } else {
            // If no valid token exists, revoke all existing tokens
            refreshTokenRepository.revokeAllByAccount(account.getId());

            // Create a new refresh token
            refreshToken = new RefreshToken();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(LocalDateTime.now().plusDays(expiredRefreshToken / (1000 * 60 * 60 * 24)));
            refreshToken.setAccount(account);
            refreshToken.setRevoked(false);
            refreshTokenRepository.save(refreshToken);
        }

        // Generate JWT token
        String token = jwtProvider.generateToken(username, account.getRole().getId() , account.getId());

        return ApiResponse.success("Login success",token);
    }

    @Override
    @Transactional
    public ApiResponse<String> signUp(RegisterRequest registerRequest) {
        if (accountRepository.existsByUserName(registerRequest.getUserName())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTED);
        }
        Account account = new Account();
        account.setUserName(registerRequest.getUserName());
        account.setPassWord(passwordEncoder.encode(registerRequest.getPassWord()));
        Role role = roleRepository.findById(2)
                .orElseThrow(() -> new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND));
        account.setRole(role);
        accountRepository.save(account);
        System.out.println("UUID account: " + account.getId());

        User user = new User();
        user.setFullName(account.getUserName());
        user.setAccount(account);
        user.setPhone("000-000-0000"); // Normalize phone number or set a default value
        userRepository.save(user);
        return ApiResponse.success("Sign up success", account.getUserName());
    }

    @Override
    @Transactional
    public ApiResponse<AccountDTO> createAccount(AccountDTO account){
        if (accountRepository.existsByUserName(account.getUserName())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTED);
        }
        Role role = roleRepository.findById(account.getRoleId())
                .orElseThrow(()-> new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND));
        Account saveAccount = accountMapper.toEntity(account);
        saveAccount.setRole(role);
        accountRepository.save(saveAccount);
        return ApiResponse.success("Create account success",accountMapper.toDTO(saveAccount));
    }

    public ApiResponse<List<AccountDTO>> getAllAccounts() {
        if (accountRepository.findAll().isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> accountDTOs = accounts.stream()
                .map(accountMapper::toDTO)
                .toList();
        return ApiResponse.success("Get all accounts success", accountDTOs);
    }

    @Override
    public ApiResponse<AccountDTO> getAccountById(UUID id) {
        Account account =  accountRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND));
        return ApiResponse.success("Get account by ID success", accountMapper.toDTO(account));
    }

    @Override
    @Transactional
    public ApiResponse<Boolean> deleteAccount(UUID userId) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        accountRepository.delete(account);
        return ApiResponse.success("Delete account success", true);
    }

    @Override
    @Transactional
    public ApiResponse<Account> updateAccount(UpdateAccountRequest updateAccountRequest, int accountId) {
//        Account existingAccountOpt = accountRepository.findById(accountId)
//                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
//        if (existingAccountOpt.isPresent()) {
//            Account existingAccount = existingAccountOpt.get();
//            Account updatedAccount = accountMapper.toEntity(accountDTO);
//            // Chỉ cập nhật những trường cần thiết
//            existingAccount.setUserName(updatedAccount.getUserName());
//            existingAccount.setPassWord(updatedAccount.getPassWord());
//            Role role = roleRepository.findById(accountDTO.getRoleId())
//                    .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));
//            existingAccount.setRole(role);
//            return ApiResponse.<Account>builder()
//                    .code(200)
//                    .message("Update account success")
//                    .result(accountRepository.save(existingAccount))
//                    .build();
//        } else {
//            throw new AppException(ErrorCode.CANT_FOUND);
//        }
        return null ;
    }

    public boolean isUsernameExists(String username) {
        Optional<Account> account = accountRepository.findByUserNameIgnoreCase(username);
        return account.isPresent();
    }
}

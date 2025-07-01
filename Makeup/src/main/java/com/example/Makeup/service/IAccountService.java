package com.example.Makeup.service;

import com.example.Makeup.dto.model.AccountDTO;
import com.example.Makeup.dto.request.RegisterRequest;
import com.example.Makeup.dto.request.UpdateAccountRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.Account;

import java.util.List;
import java.util.UUID;

public interface IAccountService {
    ApiResponse<String> authenticate(String username, String password);
    ApiResponse<String> signUp(RegisterRequest registerRequest);
    ApiResponse<AccountDTO> createAccount(AccountDTO account);
    ApiResponse<List<AccountDTO>> getAllAccounts();
    ApiResponse<AccountDTO> getAccountById(UUID id);
    ApiResponse<Boolean> deleteAccount(UUID userId);
    ApiResponse<Account> updateAccount(UpdateAccountRequest updateAccountRequest, int accountId);
}

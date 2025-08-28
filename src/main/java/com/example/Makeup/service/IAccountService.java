package com.example.Makeup.service;

import com.example.Makeup.dto.model.AccountDTO;
import com.example.Makeup.dto.request.RegisterRequest;
import com.example.Makeup.dto.request.UpdateAccountRequest;
import com.example.Makeup.dto.response.AuthResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.Account;
import java.util.List;
import java.util.UUID;

public interface IAccountService {
  AuthResponse authenticate(String username, String password);

  String signUp(RegisterRequest registerRequest);

  AccountDTO createAccount(AccountDTO account);

  List<AccountDTO> getAllAccounts();

  AccountDTO getAccountById(UUID id);

  Boolean deleteAccount(UUID userId);

  Account updateAccount(UpdateAccountRequest updateAccountRequest, int accountId);
}

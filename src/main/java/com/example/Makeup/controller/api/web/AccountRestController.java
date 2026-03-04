package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.AccountDTO;
import com.example.Makeup.dto.request.UpdateAccountRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IAccountService;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account API", description = "API for account operations")
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountRestController {

  private final IAccountService accountService;

  @Operation(summary = "Get all accounts", description = "Retrieve a list of all accounts")
  @GetMapping
  public ApiResponse<List<AccountDTO>> getAllAccounts() {
    return ApiResponse.success("Get all accounts success",accountService.getAllAccounts());
  }

  @Operation(summary = "Get account by ID", description = "Retrieve account details by account ID")
  @GetMapping("/{id}")
  public ApiResponse<AccountDTO> getAccountById(@PathVariable("id") UUID accountId) {
    return ApiResponse.success("Get account by ID success",accountService.getAccountById(accountId));
  }

  @Operation(summary = "Create a new account", description = "Create a new account with the provided details")
  @PostMapping("")
  public ApiResponse<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
    return ApiResponse.success("Create account success",accountService.createAccount(accountDTO));
  }

  @Operation(summary = "Update an existing account", description = "Update account details by account ID")
  @PutMapping("/{id}")
  public ResponseEntity<Boolean> updateAccount(
      @RequestBody UpdateAccountRequest updateAccountRequest, @PathVariable("id") UUID accountId) {
    return null; // accountService.updateAccount(updateAccountRequest, accountId);
  }

  @Operation(summary = "Delete an account", description = "Delete an account by account ID")
  @DeleteMapping("/{id}")
  public ApiResponse<Boolean> deleteAccount(@PathVariable("id") UUID accountId) {
    return ApiResponse.success("Delete account success",accountService.deleteAccount(accountId));
  }
}

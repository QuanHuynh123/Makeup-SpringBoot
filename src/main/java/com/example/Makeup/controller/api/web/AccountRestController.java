package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.AccountDTO;
import com.example.Makeup.dto.request.UpdateAccountRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IAccountService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountRestController {

  private final IAccountService accountService;

  /** Get all accounts */
  @GetMapping
  public ApiResponse<List<AccountDTO>> getAllAccounts() {
    return ApiResponse.success("Get all accounts success",accountService.getAllAccounts());
  }

  /** Get account by ID */
  @GetMapping("/{id}")
  public ApiResponse<AccountDTO> getAccountById(@PathVariable("id") UUID accountId) {
    return ApiResponse.success("Get account by ID success",accountService.getAccountById(accountId));
  }

  /** Create account */
  @PostMapping("/create")
  public ApiResponse<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
    return ApiResponse.success("Create account success",accountService.createAccount(accountDTO));
  }

  /** Update account */
  @PutMapping("/edit/{id}")
  public ResponseEntity<Boolean> updateAccount(
      @RequestBody UpdateAccountRequest updateAccountRequest, @PathVariable("id") UUID accountId) {
    return null; // accountService.updateAccount(updateAccountRequest, accountId);
  }

  /** Delete account */
  @DeleteMapping("/delete/{id}")
  public ApiResponse<Boolean> deleteAccount(@PathVariable("id") UUID accountId) {
    return ApiResponse.success("Delete account success",accountService.deleteAccount(accountId));
  }
}

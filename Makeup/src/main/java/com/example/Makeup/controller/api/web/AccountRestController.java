package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.AccountDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.mapper.AccountMapper;
import com.example.Makeup.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts") // Đường dẫn API
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    /**
     * Lấy tất cả tài khoản
     */
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.findAll();
//        List<AccountDTO> accountDTOs = accounts.stream()
//                .map(accountMapper::toDTO)
//                .toList();
        return ResponseEntity.ok(accounts);
    }

    /**
     * Lấy thông tin tài khoản theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("id") int id) {
        Optional<Account> accountOpt = accountService.findById(id);
        return accountOpt.map(account -> ResponseEntity.ok(accountMapper.toDTO(account)))
                .orElse(ResponseEntity.status(404).body(null));
    }

    /**
     * Tạo tài khoản mới
     */
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody AccountDTO accountDTO) {
        if (accountService.checkExists(accountDTO.getUserName())) {
            return ResponseEntity.status(400).body("Tài khoản đã tồn tại");
        }
        Account createdAccount = accountService.save(accountDTO);
        return ResponseEntity.ok("Tạo tài khoản thành công: " + createdAccount.getId());
    }

    /**
     * Sửa tài khoản
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> edit(@RequestBody AccountDTO accountDTO, @PathVariable("id") int id) {
        try {
            accountService.update(accountDTO, id);
            return ResponseEntity.ok("Sửa tài khoản thành công");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Không tìm thấy tài khoản");
        }
    }

    /**
     * Xóa tài khoản
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        boolean deleted = accountService.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Xóa tài khoản thành công");
        }
        return ResponseEntity.status(404).body("Không tìm thấy tài khoản");
    }
}
package com.hanjin.account.controller;

import com.hanjin.account.domain.Account;
import com.hanjin.account.dto.Account.AccountResponse;
import com.hanjin.account.dto.Account.CreateAccountRequest;
import com.hanjin.account.dto.Account.UpdateAccountRequest;
import com.hanjin.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<List<AccountResponse>> listAccounts(){
        List<Account> accountList = accountRepository.findAll();
        List<AccountResponse> list = accountList.stream().map(
                account -> getAccountResponse(account)).collect(Collectors.toList()
        );

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable("id") Long accountId){
        Account account = accountRepository.findById(accountId).orElse(new Account());
        AccountResponse response = getAccountResponse(account);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request){
        Account account = new Account();
        account.setAccountId(request.getAccountId());
        account.setLoginId(request.getLoginId());
        account.setName(request.getName());
        account.setResponseTime(LocalDateTime.now());
        account = accountRepository.save(account);

        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@RequestBody UpdateAccountRequest request, @PathVariable("id") Long accountId){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoResultException());

        account.setAccountId(accountId);
        account.setLoginId(request.getLoginId());
        account.setName(request.getName());
        account.setResponseTime(LocalDateTime.now());
        account = accountRepository.save(account);

        return new ResponseEntity<>(getAccountResponse(account), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountResponse> deleteAccount(@PathVariable("id") Long accountId){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoResultException());

        accountRepository.delete(account);

        return new ResponseEntity<>(getAccountResponse(account), HttpStatus.OK);
    }
    private AccountResponse getAccountResponse(Account account){
        AccountResponse response = new AccountResponse();
        response.setAccountId(account.getAccountId());
        response.setLoginId(account.getLoginId());
        response.setName(account.getName());
        response.setResponseTime(LocalDateTime.now());
        return response;
    }
}

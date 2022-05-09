package com.hanjin.account.controller;

import com.hanjin.account.domain.LoginHistory;
import com.hanjin.account.dto.LoginHistory.CreateLoginHistoryRequest;
import com.hanjin.account.dto.LoginHistory.LoginHistoryResponse;
import com.hanjin.account.dto.LoginHistory.UpdateLoginHistoryRequest;
import com.hanjin.account.repository.LoginHistoryRepository;
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
@RequestMapping("/accounts/{accountId}/loginHistories")
public class LoginController {

    //TODO: Account와 LoginHistory는 1:N의 관계입니다. 다만 이 둘을 어떻게 엮어야 할지는 고민해봐도 답이 쉽게 나오질 않습니다.
    //TODO: Parameter로 {accountId}를 주고 @PathVariable로 받고 있지만 1:N의 관계로 풀어내지 못하고 있습니다.
    private final LoginHistoryRepository loginHistoryRepository;

    @GetMapping()
    public ResponseEntity<List<LoginHistoryResponse>> listLoginHistories(@PathVariable("accountId") Long accountId){
        List<LoginHistory> loginHistoryList = loginHistoryRepository.findAll();
        List<LoginHistoryResponse> list = loginHistoryList.stream().map(
                loginHistory -> getLoginHistoryResponse(loginHistory)).collect(Collectors.toList()
        );

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoginHistoryResponse> getLoginHistory(@PathVariable("accountId") Long accountId, @PathVariable("id") Long loginId){
        LoginHistory loginHistory = loginHistoryRepository.findById(loginId).orElse(new LoginHistory());

        LoginHistoryResponse response = getLoginHistoryResponse(loginHistory);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LoginHistory> createLoginHistory(@PathVariable("accountId") Long accountId, @RequestBody CreateLoginHistoryRequest request){
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setHistoryId(request.getLoginId());
        loginHistory.setCreateDate(LocalDateTime.now());
        loginHistory.setLastUpdateDate(LocalDateTime.now());
        loginHistory = loginHistoryRepository.save(loginHistory);

        return new ResponseEntity<>(loginHistory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoginHistoryResponse> updateLoginHistory(@PathVariable("accountId") Long accountId, @PathVariable("id") Long loginId,
                                                                   @RequestBody UpdateLoginHistoryRequest request){
        LoginHistory loginHistory = loginHistoryRepository.findById(loginId).orElseThrow(()-> new NoResultException());

        loginHistory.setLastUpdateDate(LocalDateTime.now());
        loginHistory = loginHistoryRepository.save(loginHistory);

        return new ResponseEntity<>(getLoginHistoryResponse(loginHistory), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LoginHistoryResponse> deleteLoginHistory(@PathVariable("accountId") Long accountId, @PathVariable("id") Long loginId){
        LoginHistory loginHistory = loginHistoryRepository.findById(loginId).orElseThrow(()-> new NoResultException());

        loginHistoryRepository.delete(loginHistory);

        return new ResponseEntity<>(getLoginHistoryResponse(loginHistory), HttpStatus.OK);
    }

    private LoginHistoryResponse getLoginHistoryResponse(LoginHistory loginHistory){
        LoginHistoryResponse loginHistoryResponse = new LoginHistoryResponse();
        loginHistoryResponse.setLoginId(loginHistory.getHistoryId());
        loginHistoryResponse.setCreateDate(loginHistory.getCreateDate());
        loginHistoryResponse.setLastUpdateDate(loginHistory.getLastUpdateDate());

        return loginHistoryResponse;
    }
}

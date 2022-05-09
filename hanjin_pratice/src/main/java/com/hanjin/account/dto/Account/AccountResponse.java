package com.hanjin.account.dto.Account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountResponse {

    @JsonProperty(value = "account_id")
    private Long AccountId;

    @JsonProperty(value = "login_id")
    private String LoginId;
    private String name;

    @JsonProperty("response_time")
    private LocalDateTime responseTime;
}

package com.hanjin.account.dto.Account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateAccountRequest {

    @JsonProperty(value = "account_id")
    private Long AccountId;

    @JsonProperty(value = "login_id")
    private String LoginId;
    private String name;
}

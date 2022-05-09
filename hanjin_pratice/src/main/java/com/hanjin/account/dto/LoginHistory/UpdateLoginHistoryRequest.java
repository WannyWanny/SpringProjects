package com.hanjin.account.dto.LoginHistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateLoginHistoryRequest {

    @JsonProperty("login_id")
    private Long LoginId;

    @JsonProperty("last_update_date")
    private LocalDateTime lastUpdateDate;
}

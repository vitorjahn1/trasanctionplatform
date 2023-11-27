package com.hackerrank.tradingplatform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
@Builder
public class TransactionGetResponseDTO {

    private final String description;
    private final Double purchaseAmount;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime transactionDate;
    private final Double exchangeRate;
}

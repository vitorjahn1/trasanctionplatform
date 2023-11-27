package com.hackerrank.tradingplatform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@ToString
public class TransactionDTO implements Serializable {
    private final String description;
    private final Double purchaseAmount;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime transactionDate;

}

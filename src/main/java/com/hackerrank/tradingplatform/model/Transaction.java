package com.hackerrank.tradingplatform.model;

import com.hackerrank.tradingplatform.dto.TransactionDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@Builder
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final UUID id;
    private final String description;
    private final Double purchaseAmount;
    private final LocalDateTime transactionDate;


    public static Transaction toTrade(TransactionDTO transactionDTO){

        return Transaction.builder()
                    .id(UUID.randomUUID())
                    .description(transactionDTO.getDescription())
                    .purchaseAmount(transactionDTO.getPurchaseAmount())
                    .transactionDate(transactionDTO.getTransactionDate())
                    .build();
    }
}

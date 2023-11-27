package com.hackerrank.tradingplatform.service.trasaction;

import com.hackerrank.tradingplatform.dto.TransactionDTO;
import com.hackerrank.tradingplatform.model.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TransactionService {
    Transaction getTransactionById(final UUID id);
    void registerTrader(final TransactionDTO trade);

    boolean verifyPurchaseDate(LocalDateTime purchaseDate);


}

package com.hackerrank.tradingplatform.service.trasaction;

import com.hackerrank.tradingplatform.dto.TransactionDTO;
import com.hackerrank.tradingplatform.model.Transaction;
import com.hackerrank.tradingplatform.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction getTransactionById(final UUID id) {
        return transactionRepository.findById(id).get();
    }
    @Override
    public void registerTrader(final TransactionDTO transactionDto) {
        Transaction transaction = Transaction.toTrade(transactionDto);
        transactionRepository.save(transaction);
    }

    @Override
    public boolean verifyPurchaseDate(LocalDateTime purchaseDate) {
        LocalDateTime lastDateRate = convertDate("2020-09-30");

        return purchaseDate.isBefore(lastDateRate);
    }

    private LocalDateTime convertDate(String date){

        LocalDate data = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);

        LocalTime hora = LocalTime.MIDNIGHT;

        LocalDateTime dateTime = LocalDateTime.of(data, hora);

        return dateTime;
    }
}

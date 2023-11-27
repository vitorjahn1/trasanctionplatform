package com.hackerrank.tradingplatform.controller;

import com.hackerrank.tradingplatform.dto.TransactionDTO;
import com.hackerrank.tradingplatform.dto.TransactionGetDTO;
import com.hackerrank.tradingplatform.dto.TransactionGetResponseDTO;
import com.hackerrank.tradingplatform.service.trasaction.TransactionService;
import com.hackerrank.tradingplatform.service.treasury.TreasuryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TreasuryService treasuryService;

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity registerTrader(@RequestBody TransactionDTO trade) {
        transactionService.registerTrader(trade);

       return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json")
    public ResponseEntity<TransactionGetResponseDTO> getTransaction(final TransactionGetDTO transactionGetDTO) {
        treasuryService.findCurrencyFromCountryAndDate(transactionGetDTO);

        return ResponseEntity.ok( treasuryService.findCurrencyFromCountryAndDate(transactionGetDTO));
    }
}

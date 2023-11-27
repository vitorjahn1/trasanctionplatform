package com.hackerrank.tradingplatform.service.treasury;

import com.hackerrank.tradingplatform.dto.TransactionGetDTO;
import com.hackerrank.tradingplatform.dto.TransactionGetResponseDTO;

import java.util.Date;

public interface TreasuryService {

    TransactionGetResponseDTO findCurrencyFromCountryAndDate(final TransactionGetDTO transactionGetDTO);
}

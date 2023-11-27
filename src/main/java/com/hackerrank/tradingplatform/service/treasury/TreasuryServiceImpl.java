package com.hackerrank.tradingplatform.service.treasury;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.tradingplatform.dto.TransactionGetDTO;
import com.hackerrank.tradingplatform.dto.TransactionGetResponseDTO;
import com.hackerrank.tradingplatform.exception.CurrencyConversionException;
import com.hackerrank.tradingplatform.model.Transaction;
import com.hackerrank.tradingplatform.service.trasaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Service
public class TreasuryServiceImpl implements TreasuryService{

    @Autowired
    private TransactionService transactionService;
    private final String baseUrl = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange";

    public  String getExchangeRatesAsString(final String startDate ,final String country) {
        String countryFilter = String.join(",", country);
        String filterParameter = String.format("country_currency_desc:in:(%s),record_date:gte:%s", countryFilter, startDate);

        WebClient webClient = WebClient.create();
        String exchangeRatesAsString = webClient.get()
                .uri(uriBuilder -> uriBuilder.scheme("https")
                        .host("api.fiscaldata.treasury.gov")
                        .path("/services/api/fiscal_service/v1/accounting/od/rates_of_exchange")
                        .queryParam("fields", "country_currency_desc,exchange_rate,record_date")
                        .queryParam("filter", filterParameter)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return getExchageRate(exchangeRatesAsString);
    }

    private String getExchageRate(String exchangeRatesAsString) {
        String exchangeRate = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(exchangeRatesAsString);

             exchangeRate = jsonNode.path("meta")
                                    .path("dataFormats")
                                    .path("exchange_rate")
                                    .asText();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return exchangeRate;
    }

    @Override
    public TransactionGetResponseDTO findCurrencyFromCountryAndDate(final TransactionGetDTO transactionGetDTO) {
        Transaction transaction= transactionService.getTransactionById(UUID.fromString(transactionGetDTO.getIdTransaction()));

        validDate(transaction.getTransactionDate());

        Double exchangeRate = Double.parseDouble(getExchangeRatesAsString(convertDate(transaction.getTransactionDate()), transactionGetDTO.getCountry()));

        return TransactionGetResponseDTO.builder()
                                        .description(transaction.getDescription())
                                        .exchangeRate(exchangeRate)
                                        .purchaseAmount(transaction.getPurchaseAmount())
                                        .transactionDate(transaction.getTransactionDate())
                                        .build();
    }

    private void validDate(LocalDateTime transactionDate) {

        if(!transactionService.verifyPurchaseDate(transactionDate)){

            throw new CurrencyConversionException("A compra não pode ser convertida para a moeda de destino.");
        }

    }

    private String convertDate(final LocalDateTime transactionDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return transactionDate.format(formatter);
    }
}

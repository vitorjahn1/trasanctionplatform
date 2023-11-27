package com.hackerrank.tradingplatform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hackerrank.tradingplatform.dto.TransactionDTO;
import com.hackerrank.tradingplatform.model.Transaction;
import com.hackerrank.tradingplatform.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TransactionPlatformTests {
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    ObjectMapper om = new ObjectMapper();
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    MockMvc mockMvc;

    Map<String, TransactionDTO> testData;

    @Before
    public void setup() {
        transactionRepository.deleteAll();
        testData = getTestData();
    }

    @Test
    public void testRegisterTrader() throws Exception {

        setOMToReadLocalDateTime(om);
        for (TransactionDTO trade : testData.values()) {
            mockMvc.perform(post("/transaction/register")
                    .contentType("application/json")
                    .content(om.writeValueAsString(trade)))
                    .andExpect(status().isCreated());
        }
    }

    @Test
    public void testRetrieveErrorDatas() throws Exception {
        Transaction transaction = buildTransactionError();

        transactionRepository.saveAndFlush(transaction);
        List<Transaction> transactions = transactionRepository.findAll();

        mockMvc.perform(get("/transaction")
                        .contentType("application/json")
                        .param("idTransaction",transactions.get(0).getId().toString())
                        .param("country","Afghanistan"))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testRetrieveSucessDatas() throws Exception {
        Transaction transaction = buildTransactionSucess();

        transactionRepository.saveAndFlush(transaction);
        List<Transaction> transactions = transactionRepository.findAll();

        mockMvc.perform(get("/transaction")
                        .contentType("application/json")
                        .param("idTransaction",transactions.get(0).getId().toString())
                        .param("country","Afghanistan"))
                .andDo(print())
                .andExpect(status().isOk());

    }
    private void setOMToReadLocalDateTime(ObjectMapper om) {

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(formatter);
        javaTimeModule.addSerializer(LocalDateTime.class, localDateTimeSerializer);

        om.registerModule(javaTimeModule);
    }

    private Map<String, TransactionDTO> getTestData() {
        Map<String, TransactionDTO> data = new HashMap<>();

        TransactionDTO trade_1 = new TransactionDTO(
                "susanchandler.wchurch@buck.com",
                62.0,
                LocalDateTime.parse("2011-12-03T10:15:30"));
        data.put("trader_1", trade_1);

        TransactionDTO trade_2 = new TransactionDTO(
                "adkinsjason.paul57@collins.com",
                251.49,
                LocalDateTime.parse("2011-10-03T10:15:30"));
        data.put("trader_2", trade_2);

        return data;
    }

    private Transaction buildTransactionError(){

        return Transaction.builder().transactionDate(LocalDateTime.now())
                                    .id(UUID.fromString("bdeeca45-f661-4cc4-87c8-61f080b72fba"))
                                    .purchaseAmount(Double.parseDouble("1.00"))
                                    .description("test")
                                    .build();
    }

    private Transaction buildTransactionSucess(){

        return Transaction.builder().transactionDate(LocalDateTime.parse("2013-09-03T10:15:30"))
                .id(UUID.fromString("bdeeca45-f661-4cc4-87c8-61f080b72fba"))
                .purchaseAmount(Double.parseDouble("1.00"))
                .description("test")
                .build();
    }
}
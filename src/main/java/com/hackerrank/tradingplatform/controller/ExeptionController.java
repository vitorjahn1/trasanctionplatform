package com.hackerrank.tradingplatform.controller;
import org.springframework.http.HttpStatus;

import com.hackerrank.tradingplatform.exception.CurrencyConversionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExeptionController {

        @ExceptionHandler(CurrencyConversionException.class)
        public ResponseEntity<String> handleCurrencyConversionException(CurrencyConversionException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

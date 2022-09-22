package com.example.ticketvendingmachine.utils;

import com.example.ticketvendingmachine.model.Card;
import com.example.ticketvendingmachine.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Component
public class CheckExpiryDate {

    private static CardRepository cardRepository;

    @Autowired
    public CheckExpiryDate(CardRepository cardRepository) {
        CheckExpiryDate.cardRepository = cardRepository;
    }

    public static boolean checkExpiryDate(Long id) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime formattedCurrentDateTime = DateUtils.formatDateTime(localDateTime);

        Card card = cardRepository
                .findByCardId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Transport card data is not found."));

        LocalDateTime expiryDate = card.getExpiry_date();

        return formattedCurrentDateTime.isAfter(expiryDate);
    }
}
package com.example.ticketvendingmachine.utils;

import com.example.ticketvendingmachine.model.Card;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TransactionUtils {
    public static Card deductValue(Card card, LocalDateTime formattedLocalDateTime) {
        card.setLast_used_date(formattedLocalDateTime);
        if (card.getCommuter_type().equals("REGULAR")) {
            card.setValue(card.getValue()-15);
            card.setExpiry_date(formattedLocalDateTime.plusYears(5));
        } else {
            card.setValue(card.getValue()-10);
            card.setExpiry_date(formattedLocalDateTime.plusYears(3));
        }
        return card;
    }
    public static Map<String, Object> isInsufficientBalance(Card card) {
        Map<String, Object> errorResponse = new HashMap<>();
        boolean isInsufficient = true;
        final double NON_DISCOUNTED_FARE = 15.00;
        final double DISCOUNTED_FARE = 10.00;
        final double REMAINING_BALANCE = card.getValue();

        if (card.getCommuter_type().equals("REGULAR")) {
            errorResponse.put("isInsufficient", (REMAINING_BALANCE < NON_DISCOUNTED_FARE) == isInsufficient);
            errorResponse.put("minimumFare", NON_DISCOUNTED_FARE);
            errorResponse.put("cardType", "for non-discounted card");
            return errorResponse;
        }

        errorResponse.put("isInsufficient", (REMAINING_BALANCE < DISCOUNTED_FARE) == isInsufficient);
        errorResponse.put("minimumFare", DISCOUNTED_FARE);
        errorResponse.put("cardType", "for discounted card");
        return errorResponse;
    }
}

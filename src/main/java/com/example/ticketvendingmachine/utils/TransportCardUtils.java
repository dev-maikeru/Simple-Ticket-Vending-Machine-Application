package com.example.ticketvendingmachine.utils;

import com.example.ticketvendingmachine.model.Card;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TransportCardUtils {
    public static void setNonDiscountedCardExpiryDate(Card tc) {
        LocalDateTime lastUsedDate = tc.getLast_used_date();
        tc.setExpiry_date(lastUsedDate.plusYears(5));
    }
    public static void setDiscountedCardExpiryDate(Card tc) {
        LocalDateTime lastUsedDate = tc.getLast_used_date();
        tc.setExpiry_date(lastUsedDate.plusYears(3));
    }

    public static boolean isStartingValueWithinRange(double amountToLoad) {
        final int STARTING_MINIMUM_VALUE = 100;
        final int STARTING_MAXIMUM_VALUE = 1000;
        return amountToLoad >= STARTING_MINIMUM_VALUE && amountToLoad <= STARTING_MAXIMUM_VALUE;
    }

    public static boolean isMaximumBalance(Card card) {
        final double MAXIMUM_BALANCE = 10000.00;
        double currentLoadBalance = card.getValue();
        return currentLoadBalance == MAXIMUM_BALANCE;
    }

    public static boolean isCustomerMoneyInsufficient(double amountToLoad, double customerMoney) {
        return customerMoney < amountToLoad;
    }

    public static Map<String, Object> getChangeAndNewBalance(Card card, double amountToLoad, double customerMoney) {
        Map<String, Object> response = new HashMap<>();
        final double MAXIMUM_BALANCE = 10000.0;
        final double CURRENT_BALANCE = card.getValue();
        double change = 0;
        double newBalance = 0;

        //if the sum of current balance and given amount will exceed the maximum balance
        if ((CURRENT_BALANCE + amountToLoad) > 10000.0) {
            double deductedLoadAmount = MAXIMUM_BALANCE - CURRENT_BALANCE;
            System.out.println(deductedLoadAmount);
            card.setValue(CURRENT_BALANCE + deductedLoadAmount);
            newBalance = card.getValue();
            change = customerMoney - deductedLoadAmount;
        } else {
            card.setValue(CURRENT_BALANCE + amountToLoad);
            newBalance = card.getValue();
            change = customerMoney - amountToLoad;
        }

        response.put("change", change);
        response.put("new_balance", newBalance);

        return response;
    }
}

package com.example.ticketvendingmachine.utils;

public class GenerateCardId {
    public static long generateCardId() {
        /* return a random long of 16 length */
        long smallest = 1000_0000_0000_0000L;
        long biggest =  8000_0000_0000_0000L;

        // return a long between smallest and biggest (+1 to include biggest as well with the upper bound)
        long cardId = smallest + (long) (Math.random() * (biggest - smallest));
        return cardId;
    }

}

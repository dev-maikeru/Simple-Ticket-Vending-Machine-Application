package com.example.ticketvendingmachine.factory;

import com.example.ticketvendingmachine.model.Card;

public interface ICard {
    String getTransportCardType();
    Card createTransportCard(Card card);
}

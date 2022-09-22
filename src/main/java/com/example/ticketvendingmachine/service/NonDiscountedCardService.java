package com.example.ticketvendingmachine.service;

import com.example.ticketvendingmachine.factory.ICard;
import com.example.ticketvendingmachine.model.Card;
import com.example.ticketvendingmachine.repository.CardRepository;
import com.example.ticketvendingmachine.utils.TransportCardUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NonDiscountedCardService implements ICard {
    private final CardRepository cardRepository;

    public NonDiscountedCardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public String getTransportCardType() {
        return "Not discounted";
    }

    @Override
    @Transactional //In order to persist multiple writes on the database
    public Card createTransportCard(Card card) {
        Card tc = cardRepository.save(card);
        TransportCardUtils.setNonDiscountedCardExpiryDate(tc);

        return tc;
    }

}

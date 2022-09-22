package com.example.ticketvendingmachine.service;

import com.example.ticketvendingmachine.model.Card;
import com.example.ticketvendingmachine.repository.CardRepository;
import com.example.ticketvendingmachine.utils.DateUtils;
import com.example.ticketvendingmachine.utils.TransactionUtils;
import com.example.ticketvendingmachine.utils.TransportCardUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card getTransportCardDetails(Long id) {
        Card card = cardRepository
                .findByCardId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Transport card data is not found."));

        return card;
    }

    //For every transaction/exit, the last_used_date and expiry date must be updated
    public Card getTransactionDetails(Card card) {
        Map<String, Object> errorResponse = TransactionUtils.isInsufficientBalance(card);
        System.out.println(errorResponse);

        if (Boolean.parseBoolean(String.valueOf(errorResponse.get("isInsufficient")))) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "Insufficient balance. " +
                            "The minimum fare " + String.valueOf(errorResponse.get("cardType")) +
                            " is ₱" + Double
                            .parseDouble(String
                                    .valueOf(errorResponse.get("minimumFare"))) + "0. " +
                            "Please reload your card first.");
        }
        Card updatedCard = TransactionUtils
                .deductValue(card, DateUtils
                        .getFormattedDateTimeNow());
        cardRepository.save(updatedCard);
        return updatedCard;
    }

    @Transactional
    public Map<String, Object> reloadTransportCard(Card card, Map<String, Object> reloadingObj) {
        double amountToLoad = Double.parseDouble(String.valueOf(reloadingObj.get("load")));
        double customerMoney = Double.parseDouble(String.valueOf(reloadingObj.get("money")));

        //if the given amount to load is not from 100 to 1000
        boolean isWithinRange = TransportCardUtils.isStartingValueWithinRange(amountToLoad);
        if (!isWithinRange) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    "We only accept a load amount from ₱100 to ₱1,000");
        }

        //if the given customer money is insufficient to desired amount of load
        boolean isInsufficient = TransportCardUtils.isCustomerMoneyInsufficient(amountToLoad, customerMoney);
        if (isInsufficient) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "The amount you've given is insufficient for your desired load amount.");
        }

        //if the card is already at maximum balance
        boolean isMaxBalance = TransportCardUtils.isMaximumBalance(card);
        if (isMaxBalance) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "Sorry. We cannot process the card reloading. " +
                            "You have already reached the maximum balance in your card.");
        }

        return TransportCardUtils.getChangeAndNewBalance(card, amountToLoad, customerMoney);
    }
}
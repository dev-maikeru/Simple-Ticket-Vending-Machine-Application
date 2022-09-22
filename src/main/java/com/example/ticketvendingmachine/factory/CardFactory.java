package com.example.ticketvendingmachine.factory;

import com.example.ticketvendingmachine.service.DiscountedCardService;
import com.example.ticketvendingmachine.service.NonDiscountedCardService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CardFactory {

    private final NonDiscountedCardService nonDiscountedCardService;
    private final DiscountedCardService discountedCardService;
    private static final Map<String, ICard> transportCardObj = new HashMap<>();

    public CardFactory(NonDiscountedCardService nonDiscountedCardService, DiscountedCardService discountedCardService) {
        this.nonDiscountedCardService = nonDiscountedCardService;
        this.discountedCardService = discountedCardService;
    }

    public ICard getTransportCardInstance(String type) throws Exception {
        switch (type) {
            case "REGULAR" -> transportCardObj.put("REGULAR", this.nonDiscountedCardService);
            case "SENIOR" -> transportCardObj.put("SENIOR", this.discountedCardService);
            case "PWD" -> transportCardObj.put("PWD", this.discountedCardService);
        }
        return transportCardObj.get(type);
    }
}
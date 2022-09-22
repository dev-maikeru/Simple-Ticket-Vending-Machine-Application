package com.example.ticketvendingmachine.controller;

import com.example.ticketvendingmachine.factory.CardFactory;
import com.example.ticketvendingmachine.factory.ICard;
import com.example.ticketvendingmachine.model.Card;
import com.example.ticketvendingmachine.service.CardService;
import com.example.ticketvendingmachine.utils.CheckExpiryDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class CardController {
    private final CardFactory cardFactory;
    private final CardService cardService;
    private ICard transportCardInstance;

    public CardController(CardFactory cardFactory, CardService cardService) {
        this.cardFactory = cardFactory;
        this.cardService = cardService;
        System.out.println("Injected!");
    }

    //POST http://localhost:8080/api/transport-card/{type}
    //Save transport card details whether it is a Non-discounted card or Discounted card
    //This is triggered whenever a commuter bought transport card
    @PostMapping("/transport-card/{type}")
    public Card createTransportCard(@PathVariable("type") String type, @RequestBody Card card) throws Exception {
        //This would return an instance of whether Q-LESS Transport Service
        // or Q-LESS Discounted Transport Service
        transportCardInstance = cardFactory.getTransportCardInstance(type);
        return transportCardInstance.createTransportCard(card);
    }

    //GET http://localhost:8080/api/transport-card/{id}
    //Returns the transport card details
    //This is triggered whenever the transport card is placed on the scanner for reading
    @GetMapping("/transport-card/{id}")
    public ResponseEntity<Card> getTransportCardDetails(@PathVariable("id") Long id) {

        boolean isExpired = CheckExpiryDate.checkExpiryDate(id);
        if (isExpired) {
            return new ResponseEntity<>(cardService
                    .getTransportCardDetails(id),
                    HttpStatus.EXPECTATION_FAILED //417
            );
        }
        return new ResponseEntity<>(cardService
                .getTransportCardDetails(id),
                HttpStatus.OK //200
        );
    }

    //GET http://localhost:8080/api/transport-card/transaction/{id}
    //Saves and returns the transaction details
    //This is triggered whenever the transport card is placed on the scanner for transaction/exit
    @GetMapping("/transport-card/transaction/{id}")
    public Card getTransactionDetails(@PathVariable("id") Long id) {
        Card card = cardService.getTransportCardDetails(id);
        return cardService.getTransactionDetails(card);
    }

    //POST http://localhost:8080/api/transport-card/reload/{id}
    //Adds load amount to specific card and returns change and new balance
    //This is triggered whenever the commuter wants to reload their card
    @PostMapping("/transport-card/reload/{id}")
    public Map<String, Object> reloadTransportCard(@PathVariable("id") Long card_id, @RequestBody Map<String, Object> reloadingObj) {
        System.out.println(reloadingObj);
        Card card = cardService.getTransportCardDetails(card_id);
        return cardService.reloadTransportCard(card, reloadingObj);
    }

}

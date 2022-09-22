package com.example.ticketvendingmachine.repository;

import com.example.ticketvendingmachine.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c WHERE c.card_id = :card_id")
    Optional<Card> findByCardId(@Param("card_id") Long card_id);
}

package com.example.ticketvendingmachine.model;

import com.example.ticketvendingmachine.utils.DateUtils;
import com.example.ticketvendingmachine.utils.GenerateCardId;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transport_card", indexes = @Index(columnList = "card_id"))
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "card_id", unique = true)
    private Long card_id;

    @Column(name = "value", precision = 2)
    private double value;

    @Column(name = "status")
    private String status;

    @Column(name = "expiry_date")
    private LocalDateTime expiry_date;

    @Column(name = "last_used_date")
    private LocalDateTime last_used_date;

    @Column(name = "commuter_type")
    private String commuter_type;

    @PrePersist
    public void prePersist() {
        this.last_used_date = DateUtils.getFormattedDateTimeNow();
        this.card_id = GenerateCardId.generateCardId();
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", card_id=" + card_id +
                ", value=" + value +
                ", status='" + status + '\'' +
                ", expiry_date=" + expiry_date +
                ", last_used_date=" + last_used_date +
                ", transport_card_type='" + commuter_type + '\'' +
                '}';
    }

    public Card(){  }

    public Card(long id, Long card_id, double value, String status, LocalDateTime expiry_date, LocalDateTime last_used_date, String commuter_type) {
        this.id = id;
        this.card_id = card_id;
        this.value = value;
        this.status = status;
        this.expiry_date = expiry_date;
        this.last_used_date = last_used_date;
        this.commuter_type = commuter_type;
    }


    public long getId() {
        return id;
    }

    public Long getCard_id() {
        return card_id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(LocalDateTime expiry_date) {
        this.expiry_date = expiry_date;
    }

    public LocalDateTime getLast_used_date() {
        return last_used_date;
    }

    public void setLast_used_date(LocalDateTime last_used_date) {
        this.last_used_date = last_used_date;
    }

    public String getCommuter_type() {
        return commuter_type;
    }

    public void setCommuter_type(String commuter_type) {
        this.commuter_type = commuter_type;
    }
}


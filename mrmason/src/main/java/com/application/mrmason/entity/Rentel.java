package com.application.mrmason.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rent_assets")
public class Rentel {
    @Id
    @Column(name = "Asset_model_id")
    private String assetId;

    @Column(name = "sp_ec_userid")
    private String userId;

    @Column(name = "is_available_for_rent")
    private String isAvailRent;

    @Column(name = "amount_per_day")
    private String amountPerDay;

    @Column(name = "amount_per_30days")
    private String amountper30days;

    @Column(name = "pickup")
    private String pickup;

    @Column(name = "available_location")
    private String availableLocation;

    @Column(name = "delivery")
    private String delivery;

    @Column(name = "updated_date")
    private String updateDate;

    @PrePersist
    @PreUpdate
    private void prePersist() {
        LocalDateTime now = LocalDateTime.now();
//        String amPm = now.getHour() < 12 ? "am" : "pm"; // Determine AM or PM
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

        // Format update date
        this.updateDate = now.format(formatter);
  
        
    }
}

package com.SpringReactCollective.The.Online.Outfitters.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BankDetails {

  private String accountNumber;

  private String accountHolderName;

  private String ifscCode;

}

package com.SpringReactCollective.The.Online.Outfitters.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User customer;

    @OneToOne
    private Orders orders;

    @ManyToOne
    private Seller seller;

    private LocalDateTime date = LocalDateTime.now();
}

package com.SpringReactCollective.The.Online.Outfitters.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BusinessDetails {

    private String businessName;

    private String businessEmail;

    private String businessMobile;

    private String businessAddress;

    private String logo;

    private String banner;

}

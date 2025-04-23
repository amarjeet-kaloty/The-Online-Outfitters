package com.SpringReactCollective.The.Online.Outfitters.Repository;

import com.SpringReactCollective.The.Online.Outfitters.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long>{

    Seller findByEmail(String email);

}

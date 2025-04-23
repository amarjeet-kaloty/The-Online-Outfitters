package com.SpringReactCollective.The.Online.Outfitters.Repository;

import com.SpringReactCollective.The.Online.Outfitters.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    VerificationCode findByEmail(String email);

}

package com.SpringReactCollective.The.Online.Outfitters.Repository;

import com.SpringReactCollective.The.Online.Outfitters.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}

package com.SpringReactCollective.The.Online.Outfitters.response;

import com.SpringReactCollective.The.Online.Outfitters.domain.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;

    private String message;

    private USER_ROLE role;

}

package com.xxx.WhatsAppWebClone.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String jwt;
    private boolean isAuth;
}

package com.xxx.WhatsAppWebClone.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    private String full_name;
    private String email;
    private String profile_picture;
    private String password;
}

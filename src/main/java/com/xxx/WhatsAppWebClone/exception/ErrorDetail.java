package com.xxx.WhatsAppWebClone.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class ErrorDetail {
    private String error;
    private String message;
    private LocalDateTime localDateTime;
}

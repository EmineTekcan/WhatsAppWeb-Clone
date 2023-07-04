package com.xxx.WhatsAppWebClone.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequest {

    private Integer userId;
    private Integer chatId;
    private String content;
}

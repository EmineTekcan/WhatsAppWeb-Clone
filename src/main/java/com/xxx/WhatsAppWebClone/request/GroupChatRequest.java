package com.xxx.WhatsAppWebClone.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatRequest {

    private List<Integer> userIds;
    private String chat_name;
    private String chat_image;
}

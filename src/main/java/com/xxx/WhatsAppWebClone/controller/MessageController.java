package com.xxx.WhatsAppWebClone.controller;

import com.xxx.WhatsAppWebClone.exception.ChatException;
import com.xxx.WhatsAppWebClone.exception.MessageException;
import com.xxx.WhatsAppWebClone.exception.UserException;
import com.xxx.WhatsAppWebClone.model.Message;
import com.xxx.WhatsAppWebClone.model.User;
import com.xxx.WhatsAppWebClone.request.SendMessageRequest;
import com.xxx.WhatsAppWebClone.response.ApiResponse;
import com.xxx.WhatsAppWebClone.service.MessageService;
import com.xxx.WhatsAppWebClone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private MessageService messageService;
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageRequest messageRequest, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User user=userService.findUserProfile(jwt);
        messageRequest.setUserId(user.getId());
        Message message=messageService.sendMessage(messageRequest);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessages(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User user=userService.findUserProfile(jwt);
        List<Message> messages=messageService.getChatMessages(chatId,user);
            return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessage(@PathVariable Integer messageId, @RequestHeader("Authorization") String jwt) throws UserException, MessageException {
        User user=userService.findUserProfile(jwt);
        messageService.deleteMessage(messageId,user);
        ApiResponse apiResponse=ApiResponse.builder()
                .message("Message successfully deleted ")
                .status(true)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

}

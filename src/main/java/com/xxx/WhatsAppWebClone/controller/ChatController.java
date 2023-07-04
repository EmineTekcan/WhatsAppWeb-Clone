package com.xxx.WhatsAppWebClone.controller;

import com.xxx.WhatsAppWebClone.exception.ChatException;
import com.xxx.WhatsAppWebClone.exception.UserException;
import com.xxx.WhatsAppWebClone.model.Chat;
import com.xxx.WhatsAppWebClone.model.User;
import com.xxx.WhatsAppWebClone.request.GroupChatRequest;
import com.xxx.WhatsAppWebClone.request.SingleChatRequest;
import com.xxx.WhatsAppWebClone.response.ApiResponse;
import com.xxx.WhatsAppWebClone.service.Impl.ChatServiceImpl;
import com.xxx.WhatsAppWebClone.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatServiceImpl chatService;
    private final UserServiceImpl userService;

    @PostMapping("/single")
    public ResponseEntity<Chat> createChat(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization") String jwt ) throws UserException, ChatException {

        User user=userService.findUserProfile(jwt);
        Chat chat=chatService.createChat(user,singleChatRequest.getUserId());

        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroup(@RequestBody GroupChatRequest groupChatRequest,@RequestHeader("Authorization") String jwt) throws UserException{
        User reqUser=userService.findUserProfile(jwt);
        Chat groupChat=chatService.createGroup(groupChatRequest,reqUser);
        return new ResponseEntity<>(groupChat,HttpStatus.CREATED);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatById(@PathVariable("chatId") Integer chatId,@RequestHeader("Authorization") String jwt) throws UserException,ChatException{
        Chat chat=chatService.findChatById(chatId);
        return new ResponseEntity<>(chat,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> getAllChatByUserId(@RequestHeader("Authorization") String jwt) throws UserException{
        User user=userService.findUserProfile(jwt);
        List<Chat> chats=chatService.findAllChatByUserId(user.getId());
        return new ResponseEntity<>(chats,HttpStatus.OK);
    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroup(@PathVariable Integer chatId, @PathVariable Integer userId,@RequestHeader("Authorization") String jwt) throws UserException,ChatException{
        User user=userService.findUserProfile(jwt);
        Chat chat=chatService.addUserToGroup(userId,chatId,user);
        return new ResponseEntity<>(chat,HttpStatus.CONFLICT);
    }

    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUserFromGroup(@PathVariable Integer chatId, @PathVariable Integer userId,@RequestHeader("Authorization") String jwt) throws UserException,ChatException{
        User user=userService.findUserProfile(jwt);
        Chat chat=chatService.removeFromGroup(userId,chatId,user);
        return new ResponseEntity<>(chat,HttpStatus.CONFLICT);
    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> removeUserFromGroup(@PathVariable Integer chatId,@RequestHeader("Authorization") String jwt) throws UserException,ChatException{
        User user=userService.findUserProfile(jwt);
        chatService.deleteChat(chatId,user.getId());
        ApiResponse apiResponse=ApiResponse.builder()
                .message("Delete chat successfully")
                .status(true)
                .build();

        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }




}

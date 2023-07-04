package com.xxx.WhatsAppWebClone.service.Impl;

import com.xxx.WhatsAppWebClone.exception.ChatException;
import com.xxx.WhatsAppWebClone.exception.MessageException;
import com.xxx.WhatsAppWebClone.exception.UserException;
import com.xxx.WhatsAppWebClone.model.Chat;
import com.xxx.WhatsAppWebClone.model.Message;
import com.xxx.WhatsAppWebClone.model.User;
import com.xxx.WhatsAppWebClone.repository.MessageRepository;
import com.xxx.WhatsAppWebClone.request.SendMessageRequest;
import com.xxx.WhatsAppWebClone.service.ChatService;
import com.xxx.WhatsAppWebClone.service.MessageService;
import com.xxx.WhatsAppWebClone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChatService chatService;

    @Override
    public Message sendMessage(SendMessageRequest sendMessageRequest) throws UserException, ChatException {

        User user=userService.findUserById(sendMessageRequest.getUserId());
        Chat chat=chatService.findChatById(sendMessageRequest.getChatId());

        Message message=Message.builder()
                .content(sendMessageRequest.getContent())
                .user(user)
                .chat(chat)
                .timestamp(LocalDateTime.now())
                .build();

        return message;
    }

    @Override
    public List<Message> getChatMessages(Integer chatId,User reqUser) throws ChatException,UserException {

        Chat chat=chatService.findChatById(chatId);

        if (chat.getUsers().contains(reqUser)){
            throw new UserException("You are not releted to this chat "+chat.getId());
        }
        List<Message> messages=messageRepository.findByChatId(chat.getId());
        return messages;

    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {

        Optional<Message> opt=messageRepository.findById(messageId);

        if (opt.isPresent()){
           return opt.get();
        }

        throw new MessageException("Message not found with id "+messageId);
    }

    @Override
    public void deleteMessage(Integer messageId,User reqUser) throws MessageException,UserException {

        Message message=findMessageById(messageId);

        if (message.getUser().getId().equals(reqUser.getId())){
            messageRepository.deleteById(messageId);
        }

        throw new UserException("You can not delete anather user's message "+reqUser.getFull_name());
    }
}

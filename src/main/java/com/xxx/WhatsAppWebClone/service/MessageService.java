package com.xxx.WhatsAppWebClone.service;

import com.xxx.WhatsAppWebClone.exception.ChatException;
import com.xxx.WhatsAppWebClone.exception.MessageException;
import com.xxx.WhatsAppWebClone.exception.UserException;
import com.xxx.WhatsAppWebClone.model.Message;
import com.xxx.WhatsAppWebClone.model.User;
import com.xxx.WhatsAppWebClone.request.SendMessageRequest;

import java.util.List;

public interface MessageService {

    public Message sendMessage(SendMessageRequest sendMessageRequest) throws UserException, ChatException;

    public List<Message> getChatMessages(Integer chatId, User reqUser) throws ChatException,UserException;

    public Message findMessageById(Integer messageId) throws MessageException,UserException;

    public void deleteMessage(Integer messageId,User reqUser) throws MessageException,UserException;


}

package com.xxx.WhatsAppWebClone.service.Impl;

import com.xxx.WhatsAppWebClone.exception.ChatException;
import com.xxx.WhatsAppWebClone.exception.UserException;
import com.xxx.WhatsAppWebClone.model.Chat;
import com.xxx.WhatsAppWebClone.model.User;
import com.xxx.WhatsAppWebClone.repository.ChatRepository;
import com.xxx.WhatsAppWebClone.request.GroupChatRequest;
import com.xxx.WhatsAppWebClone.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserServiceImpl userService;

    @Override
    public Chat createChat(User reqUser, Integer userId2) throws UserException {
        User user=userService.findUserById(userId2);
        Chat isChatExist=chatRepository.findSingleChatByUserIds(reqUser,user);

        if (isChatExist !=null){
            return isChatExist;
        }
        Chat chat=new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setIsGroup(false);
        return chat;
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat=chatRepository.findById(chatId);

        if(chat.isPresent()){
            return chat.get();
        }
        throw new ChatException("Chat not found with id: "+chatId);
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        User user=userService.findUserById(userId);

        List<Chat> chats=chatRepository.findChatByUserid(user.getId());

        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest request, User reqUser) throws UserException {
        Chat group=new Chat();
        group.setIsGroup(true);
        group.setChat_image(request.getChat_image());
        group.setChat_name(request.getChat_name());
        group.setCreatedBy(reqUser);
        group.getAdmins().add(reqUser);

        for (Integer userId:request.getUserIds()){
            User user=userService.findUserById(userId);
            group.getUsers().add(user);
        }

        return chatRepository.save(group);
    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt=chatRepository.findById(chatId);
        User user=userService.findUserById(userId);

        if (opt.isPresent()){
            Chat chat=opt.get();

            if (chat.getAdmins().contains(reqUser)){
                chat.getUsers().add(user);
                return chatRepository.save(chat);
            }
            else{
                throw new UserException("You are not admin!");
            }
        }

        throw new ChatException("Chat not found with id: "+chatId);
    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException,UserException {

        Optional<Chat> opt=chatRepository.findById(chatId);

        if(opt.isPresent()){
            Chat chat=opt.get();
            if (chat.getAdmins().contains(reqUser)){
                chat.setChat_name(groupName);
                return chatRepository.save(chat);
            }else{
                throw new UserException("You are not admin");
            }
        }

        throw new ChatException("Chat not found with id: "+chatId);
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt=chatRepository.findById(chatId);
        User user=userService.findUserById(userId);
        if (opt.isPresent()){
            Chat chat=opt.get();

            if (chat.getAdmins().contains(reqUser)){
                chat.getUsers().remove(user);
                return chatRepository.save(chat);
            } else if (chat.getUsers().contains(reqUser)) {

                if (user.getId().equals(reqUser.getId())){
                    chat.getUsers().remove(user);
                    return chatRepository.save(chat);
                }

                throw new UserException("You are not admin");

            }

            throw new UserException("You are not admin");

        }

        throw new ChatException("Chat not found with id: "+chatId);
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        Optional<Chat> opt=chatRepository.findById(chatId);

        if (opt.isPresent()){
            Chat chat=opt.get();
            chatRepository.delete(chat);
        }

        throw new ChatException("Chat not found with id: "+chatId);

    }
}

package com.xxx.WhatsAppWebClone.repository;

import com.xxx.WhatsAppWebClone.model.Chat;
import com.xxx.WhatsAppWebClone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Integer> {

    @Query("select c from Chat c join c.users u where u.id=:userId ")
    public List<Chat> findChatByUserid(@Param("userId") Integer userId);

    @Query("select c from Chat c where c.isGroup=false And :user Member of c.users And :reqUser Member of c.users")
    public Chat findSingleChatByUserIds(@Param("user") User user,@Param("reqUser") User reqUser);
}

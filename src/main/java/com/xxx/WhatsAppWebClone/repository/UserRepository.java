package com.xxx.WhatsAppWebClone.repository;

import com.xxx.WhatsAppWebClone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByEmail(String email);

    @Query("select u from User u where u.full_name like  %:query% or u.email like %:query%")
    public List<User> searchUser(@Param("query") String query);
}

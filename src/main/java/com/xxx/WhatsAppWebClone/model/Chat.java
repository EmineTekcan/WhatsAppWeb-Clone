package com.xxx.WhatsAppWebClone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "chats")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String chat_name;
    private String chat_image;

    @ManyToMany
    private HashSet<User> admins=new HashSet<>();

    @Column(name = "is_group")
    private Boolean isGroup;

    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;

    @ManyToMany
    private HashSet<User> users=new HashSet<>();

    @OneToMany
    private List<Message> messages=new ArrayList<>();

}

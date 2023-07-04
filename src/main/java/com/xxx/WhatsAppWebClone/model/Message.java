package com.xxx.WhatsAppWebClone.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String content;

    private LocalDateTime timestamp;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

}

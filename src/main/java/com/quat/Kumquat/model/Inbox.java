package com.quat.Kumquat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inbox")
public class Inbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long idInbox;
    @Column(nullable = false)
    private Long idSender;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String msg;
    @Column(nullable = false)
    private Date timeSent;
    @ManyToOne
    @JoinColumn(name="idUser")
    private User receiver;

}
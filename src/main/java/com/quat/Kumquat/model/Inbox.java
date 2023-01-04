package com.quat.Kumquat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "reciver_id",nullable = false)
    private User reciver;

    @ManyToOne()
    @JoinColumn(name = "sender_id",nullable = false)
    private User sender;


    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String msg;
}
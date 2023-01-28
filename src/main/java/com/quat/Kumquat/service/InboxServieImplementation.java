package com.quat.Kumquat.service;

import com.quat.Kumquat.model.Inbox;
import com.quat.Kumquat.model.User;
import com.quat.Kumquat.repository.InboxRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InboxServieImplementation implements InboxService{

    private InboxRepository inboxRepository;

    public InboxServieImplementation(InboxRepository inboxRepository) {
        this.inboxRepository=inboxRepository;
    }

    public void sendMessage(User sender, User receiver, String title, String description){
        Inbox msg = new Inbox();
        msg.setMsg(description);
        msg.setSubject(title);
        msg.setReceiver(receiver);
        msg.setIdSender(sender.getIdUser());
        msg.setTimeSent(new Date());
        inboxRepository.save(msg);
    }
}

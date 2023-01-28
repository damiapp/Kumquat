package com.quat.Kumquat.service;

import com.quat.Kumquat.model.User;

public interface InboxService {
    void sendMessage(User sender, User reciver, String title, String description);
}

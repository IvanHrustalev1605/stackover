package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.service.abstracts.model.GroupChatService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class GroupChatServiceImpl extends ReadWriteServiceImpl<GroupChat, Long> implements GroupChatService {

    public GroupChatServiceImpl(ReadWriteDao<GroupChat, Long> readWriteDao) {
        super(readWriteDao);
    }
}

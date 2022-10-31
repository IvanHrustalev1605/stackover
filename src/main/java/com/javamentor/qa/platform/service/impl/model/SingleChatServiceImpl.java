package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.service.abstracts.model.SingleChatService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SingleChatServiceImpl extends ReadWriteServiceImpl<SingleChat, Long> implements SingleChatService {

    public SingleChatServiceImpl(ReadWriteDao<SingleChat, Long> readWriteDao) {
        super(readWriteDao);
    }
}

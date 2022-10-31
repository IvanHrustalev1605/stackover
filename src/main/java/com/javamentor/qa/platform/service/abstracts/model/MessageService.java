package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface MessageService extends ReadWriteService<Message, Long> {
}

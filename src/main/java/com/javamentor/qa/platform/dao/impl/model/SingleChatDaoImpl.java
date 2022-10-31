package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.SingleChatDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SingleChatDaoImpl extends ReadWriteDaoImpl<SingleChat, Long> implements SingleChatDao {

    @PersistenceContext
    private EntityManager entityManager;
}

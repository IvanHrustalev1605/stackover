package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VerifTokenDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.registration.VerificationToken;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.VerifTokenService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerifTokenServiceImpl extends ReadWriteServiceImpl<VerificationToken, Long> implements VerifTokenService {
    private VerifTokenDao verifTokenDao;
    public VerifTokenServiceImpl(ReadWriteDao<VerificationToken, Long> readWriteDao, VerifTokenDao verifTokenDao) {
        super(readWriteDao);
        this.verifTokenDao = verifTokenDao;
    }

    @Override
    @Transactional
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        myToken.setId(user.getId());
        myToken.setToken(token);
        verifTokenDao.persist(myToken);
    }
}

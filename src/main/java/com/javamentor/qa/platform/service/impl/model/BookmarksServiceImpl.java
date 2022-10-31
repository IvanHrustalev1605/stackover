package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.service.abstracts.model.BookmarksService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BookmarksServiceImpl extends ReadWriteServiceImpl<BookMarks, Long> implements BookmarksService {

    public BookmarksServiceImpl(ReadWriteDao<BookMarks, Long> readWriteDao) {
        super(readWriteDao);
    }
}

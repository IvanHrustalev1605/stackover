package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.TagAlreadyExistsException;
import com.javamentor.qa.platform.exception.TagNotFoundException;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IgnoredTagServiceImpl extends ReadWriteServiceImpl<IgnoredTag, Long> implements IgnoredTagService {

    private final TagDtoService tagDtoService;
    private final TagService tagService;
    private final IgnoredTagDao ignoredTagDao;

    public IgnoredTagServiceImpl(ReadWriteDao<IgnoredTag, Long> readWriteDao,
                                 TagDtoService tagDtoService, TagService tagService,
                                 IgnoredTagDao ignoredTagDao) {
        super(readWriteDao);
        this.tagDtoService = tagDtoService;
        this.tagService = tagService;
        this.ignoredTagDao = ignoredTagDao;
    }


    @Override
    @Transactional
    public Optional<TagDto> addTagToIgnoreList(Long tagId, User user) {

        //check for the record is already exist
        if (ignoredTagDao.getByIdAndUser(tagId, user).isPresent()) {
            throw new TagAlreadyExistsException("Тег уже был добавлен в игнорируемые ранее.");
        }

        //check for required tag is exist
        if (tagDtoService.getById(tagId).isEmpty()) {
            throw new TagNotFoundException("Тег с таким id не найден.");
        }

        ignoredTagDao.persist(new IgnoredTag(tagService.getById(tagId).get(), user));
        return tagDtoService.getById(tagId);
    }
}

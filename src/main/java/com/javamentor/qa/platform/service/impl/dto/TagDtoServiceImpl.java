package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagDtoServiceImpl implements TagDtoService {
    private final TagDtoDao tagDtoDao;

    @Autowired
    public TagDtoServiceImpl(TagDtoDao tagDtoDao) {
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    public Optional<List<RelatedTagDto>> getTopTags() {
        return tagDtoDao.getTopTags();
    }
}

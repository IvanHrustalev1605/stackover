package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagDtoServiceImpl implements TagDtoService {

    private final TagDtoDao tagDtoDao;

    public TagDtoServiceImpl(TagDtoDao tagDtoDao) {
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    public Optional<TagDto> getById(Long id) {
        return tagDtoDao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<RelatedTagDto>> getTopTags() {
        return tagDtoDao.getTopTags();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<TagDto>> getTop3TagsByUserId(Long id) {
        return tagDtoDao.getTop3TagsByUserId(id);
    }
}

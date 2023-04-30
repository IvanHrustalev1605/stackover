package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagDtoServiceImpl implements TagDtoService {
    private TagDtoDao tagDtoDao;

    public TagDtoServiceImpl(TagDtoDao tagDtoDao) {
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    public List<RelatedTagDto> getTopTags() {
        return tagDtoDao.getTopTags();
    }

    @Override
    public List<TagDto> getTop3TagsByUserId(Long id) {
        return tagDtoDao.getTop3TagsByUserId(id);
    }
}

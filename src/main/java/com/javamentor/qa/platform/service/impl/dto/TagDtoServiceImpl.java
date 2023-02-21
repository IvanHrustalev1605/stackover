package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.webapp.converters.TagConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagDtoServiceImpl implements TagDtoService {
    private final TagService tagService;
    private final TagDtoDao tagDtoDao;
    private final TagConverter tagConverter;

    @Override
    public List<Tag> checkTags(List<TagDto> tagDtos) {

        List<Tag> tagList = new ArrayList<>();
        List<Tag> allTags = tagService.getAll();
        Tag newTag = new Tag();
        for (Tag tag : allTags) {               //перебираем текущие тэги со всеми, нашли пару - вернули, иначе персист
            for (TagDto tagDto : tagDtos) {
                if (tag.getName().equals(tagDto.getName())) {
                    newTag = tag;
                } else {
                    newTag.setName(tagDto.getName());
                    tagService.persist(newTag);
                }
                tagList.add(newTag);
            }
        }
        return tagList;
    }

    @Override
    public List<TagDto> getTop3TagsByUserId(Long userId) {
        return tagDtoDao.getTop3TagsByUserId(userId)
                        .stream()
                        .map(tagConverter::tagToTagDto)
                        .collect(Collectors.toList());
    }

    @Override
    public List<RelatedTagDto> getTenTopTags() {
        return tagDtoDao.getTopTags();
    }
}

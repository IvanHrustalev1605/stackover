package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.converters.IgnoredTagConverter;
import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.IgnoredTagDtoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IgnoredTagDtoServiceImpl implements IgnoredTagDtoService {
    private final IgnoredTagDao ignoredTagDao;
    private final IgnoredTagConverter ignoredTagConverter;

    public IgnoredTagDtoServiceImpl(
            IgnoredTagDao ignoredTagDao,
            IgnoredTagConverter ignoredTagConverter
    ) {
        this.ignoredTagDao = ignoredTagDao;
        this.ignoredTagConverter = ignoredTagConverter;
    }

    @Override
    public List<IgnoredTagDto> getIgnoredTagsByUserId(Long userId) {
        return ignoredTagDao
                .getIgnoredTagsByUserId(userId)
                .stream()
                .map(ignoredTagConverter::ignoredTagToIgnoredTagDto)
                .collect(Collectors.toList());
    }
}

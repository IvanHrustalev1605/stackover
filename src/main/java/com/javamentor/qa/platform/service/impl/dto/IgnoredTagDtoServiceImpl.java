package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.IgnoredTagDtoService;
import com.javamentor.qa.platform.webapp.converters.IgnoredTagConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IgnoredTagDtoServiceImpl implements IgnoredTagDtoService {
    private final IgnoredTagDao ignoredTagDao;
    private final IgnoredTagConverter ignoredTagConverter;


    @Override
    public List<IgnoredTagDto> getIgnoredTagsByUserId(Long userId) {
        return ignoredTagDao.getIgnoredTagByUserId(userId)
                .stream()
                .map(ignoredTagConverter::IgnoredTagToIgnoredTagDto)
                .collect(Collectors.toList());
    }
}

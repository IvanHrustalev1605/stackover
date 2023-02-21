package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.IgnoredTagDtoDao;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.IgnoredTagDtoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IgnoredTagDtoServiceImpl implements IgnoredTagDtoService {
    private final IgnoredTagDtoDao ignoredTagDtoDao;

    public IgnoredTagDtoServiceImpl(IgnoredTagDtoDao ignoredTagDtoDao) {
        this.ignoredTagDtoDao = ignoredTagDtoDao;
    }

    @Override
    public List<IgnoredTagDto> getIgnoredTagsByUserId(Long userId) {
        return ignoredTagDtoDao.getAllByUserId(userId);
    }
}

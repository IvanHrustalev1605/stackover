package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TrackedTagDtoDao;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TrackedTagDtoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackedTagDtoServiceImpl implements TrackedTagDtoService {

    private final TrackedTagDtoDao trackedTagDtoDao;

    public TrackedTagDtoServiceImpl(TrackedTagDtoDao trackedTagDtoDao) {
        this.trackedTagDtoDao = trackedTagDtoDao;
    }

    @Override
    public List<TrackedTagDto> getAllByUserId(Long id) {
        return trackedTagDtoDao.getAllByUserId(id);
    }
}

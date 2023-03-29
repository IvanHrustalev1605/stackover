package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TrackedTagDtoDao;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TrackedTagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrackedTagDtoServiceImpl implements TrackedTagDtoService {
private final TrackedTagDtoDao trackedTagDtoDao;
    private final TrackedTagService trackedTagService;

    public TrackedTagDtoServiceImpl(TrackedTagDtoDao trackedTagDtoDao, TrackedTagService trackedTagService) {
        this.trackedTagDtoDao = trackedTagDtoDao;
        this.trackedTagService = trackedTagService;
    }

    @Override
    public Optional<TrackedTagDto> getTrackedDtoByTagId(Long TagId) {
        Long l = trackedTagService.getTrackedTagIdByTagId(TagId).get();
        return trackedTagDtoDao.getTrackedDtoByTrackedTagId(l);
    }



}

package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TrackedTagDtoDao;
import com.javamentor.qa.platform.exception.TagNotFoundException;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TrackedTagDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrackedTagDtoServiceImpl implements TrackedTagDtoService {
    private final TrackedTagDtoDao trackedTagDtoDao;

    @Override
    public List<TrackedTagDto> getAllByUserId(Long id) {
        Optional<List<TrackedTagDto>> optionalTrackedTagDtoList = trackedTagDtoDao.getAllByUserId(id);

        if (optionalTrackedTagDtoList.isPresent()) {
            return optionalTrackedTagDtoList.get();
        } else {
            throw new TagNotFoundException("У данного юзера тэги не найдены");
        }
    }
}

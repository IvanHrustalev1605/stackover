package com.javamentor.qa.platform.service.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDto;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaginationUserDtoServiceImpl extends PaginationDtoServiceAbstract<UserDto> {

    public PaginationUserDtoServiceImpl(Map<String, PaginationDto<UserDto>> paginationDaoMap) {
        super(paginationDaoMap);
    }

    @Override
    public PageDto<UserDto> getPageDto(Map<String, Object> parameters) {
        return super.getPageDto(parameters);
    }


}

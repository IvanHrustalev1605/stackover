package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PaginationDtoDao;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Service;
import java.util.Map;
@Service
public class UserPaginationDtoServiceImpl extends PaginationDtoServiceAbstract<UserDto>  {

    public UserPaginationDtoServiceImpl(Map<String, PaginationDtoDao<UserDto>> paginationDaoMap) {
        super(paginationDaoMap);
    }

}

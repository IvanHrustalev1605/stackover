package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.dao.impl.dto.UserDtoPaginationDtoImpl;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserDtoServiceImpl extends ReadWriteServiceImpl<UserDto, Long> implements UserDtoService {

    private final UserDtoPaginationDtoImpl userDtoPaginationDto;
    private final UserDtoDao userDtoDao;
    @Autowired
    public UserDtoServiceImpl(ReadWriteDao<UserDto, Long> readWriteDao, UserDtoPaginationDtoImpl userDtoPaginationDto, UserDtoDao userDtoDao) {
        super(readWriteDao);
        this.userDtoPaginationDto = userDtoPaginationDto;
        this.userDtoDao = userDtoDao;
    }
    @Override
    public Optional <UserDto> getById(Long id){
        return userDtoDao.getById(id);
    }

    @Override
    public List<UserDto> getAll(){
        return userDtoDao.getAll();
    }

    @Override
    public PageDto<UserDto> getPageDTO(Map<String, Object> param) {

        PageDto<UserDto> page = new PageDto<>();

        page.setItemsOnPage((Integer) param.get("itemsOnPage"));
        page.setCurrentPageNumber((Integer) param.get("currentPageNumber"));
        page.setItems(userDtoPaginationDto.getItems(param));
        page.setTotalResultCount(userDtoPaginationDto.getTotalResultCount(param));

        int totalPage = page.getTotalResultCount() % page.getItemsOnPage() == 0 ?
                page.getTotalResultCount() / page.getItemsOnPage() :
                page.getTotalResultCount() / page.getItemsOnPage() + 1;

        page.setTotalPageCount(totalPage);

        return page;
    }
}
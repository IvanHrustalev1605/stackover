package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.exception.TagAlreadyExistsException;
import com.javamentor.qa.platform.exception.TagNotFoundException;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.IgnoredTagDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.TrackedTagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/tag/")
public class ResourceTagController {

    private final TrackedTagDtoService trackedTagDtoService;
    private final IgnoredTagDtoService ignoredTagDtoService;
    private final TrackedTagService trackedTagService;

    public ResourceTagController(
            IgnoredTagDtoService ignoredTagDtoService,
            TrackedTagDtoService trackedTagDtoService,
            TrackedTagService trackedTagService
    ) {
        this.ignoredTagDtoService = ignoredTagDtoService;
        this.trackedTagDtoService = trackedTagDtoService;
        this.trackedTagService = trackedTagService;
    }


    @GetMapping("/ignored")
    @ApiOperation("Возвращает все теги, который пользователь добавил в игнор")
    @ApiResponse(responseCode = "200", description = "Теги успешно получены")
    @ApiResponse(responseCode = "404", description = "NOT FOUND - У авторизированного пользователя нет отслеживаемых тегов")
    public ResponseEntity<List<IgnoredTagDto>> getUserIgnoredTags(@ApiParam("Авторизированный пользователь") @AuthenticationPrincipal User user) {
        return Optional.ofNullable(ignoredTagDtoService.getIgnoredTagsByUserId(user.getId()))
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/tracked")
    @ApiOperation("Добавление тега в отслеживаемые пользователем")
    @ApiResponse(responseCode = "200", description = "Тег добавлен в отслеживаемые")
    @ApiResponse(responseCode = "400", description = "Тег уже добавлен в отслеживаемые")
    @ApiResponse(responseCode = "404", description = "Тег не найден")
    public ResponseEntity<TagDto> addTagToTracked(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        return trackedTagService
                .saveTrackedTagByTagAndUser(id, user)
                .map(ResponseEntity::ok)
                .get();
    }

    @ApiOperation("Возвращает все отслеживаемые теги авторизированного пользователя")
    @ApiResponse(responseCode = "200", description = "SUCCESSFULLY - Успешное получение данных")
    @ApiResponse(responseCode = "404", description = "NOT FOUND - У авторизированного пользователя нет отслеживаемых тегов")
    @GetMapping(value = "/tracked", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrackedTagDto>> getAllTrackedTagDtoOfAuthorizedUser(@ApiParam("Авторизированный пользователь") @AuthenticationPrincipal User user) {
        List<TrackedTagDto> trackedTagDtoList = trackedTagDtoService.getAllByUserId(user.getId());
        if (trackedTagDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trackedTagDtoList, HttpStatus.OK);
    }

}

package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.IgnoredTagDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.TrackedTagDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/tag")
public class ResourceTagController {
    private final IgnoredTagDtoService ignoredTagDtoService;
    private final TrackedTagDtoService trackedTagDtoService;

    public ResourceTagController(IgnoredTagDtoService ignoredTagDtoService, TrackedTagDtoService trackedTagDtoService) {
        this.ignoredTagDtoService = ignoredTagDtoService;
        this.trackedTagDtoService = trackedTagDtoService;
    }

    @GetMapping("/ignored")
    @ApiOperation("Возвращает все теги, который пользователь добавил в игнор")
    @ApiResponse(responseCode = "200", description = "Теги успешно получены")
    public ResponseEntity<List<IgnoredTagDto>> getUserIgnoredTags(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ignoredTagDtoService.getIgnoredTagsByUserId(user.getId()));
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

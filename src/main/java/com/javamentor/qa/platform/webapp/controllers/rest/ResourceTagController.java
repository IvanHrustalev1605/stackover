package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@AllArgsConstructor
@RestController
@Tag(name = "Tag контроллер", description = "Api для работы с тегами")
@RequestMapping("/api/user/tag")
public class ResourceTagController {

    private final TrackedTagService trackedTagService;

    @GetMapping("/tracked")
    @ApiOperation(value = "Возвращает все теги пользователя")
    @ApiResponse(responseCode = "200", description = "Успешное получение данных")
    @ApiResponse(responseCode = "404", description = "У пользователя нет отслеживаемых тегов")
    public ResponseEntity<List<TrackedTagDto>> getListUserTrackedDto(@AuthenticationPrincipal User user) {
        return trackedTagService.getListUserTrackedDto(user) != null ? new ResponseEntity<>(trackedTagService.getListUserTrackedDto(user), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
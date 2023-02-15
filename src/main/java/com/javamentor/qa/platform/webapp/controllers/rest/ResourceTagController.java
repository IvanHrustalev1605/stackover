package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.exception.TagAlreadyExistsException;
import com.javamentor.qa.platform.exception.TagNotFoundException;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.IgnoredTagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/tag/")
public class ResourceTagController {

    private final TrackedTagService trackedTagService;
    private final IgnoredTagDtoService ignoredTagDtoService;

    public ResourceTagController(TrackedTagService trackedTagService,
                                 IgnoredTagDtoService ignoredTagDtoService) {
        this.trackedTagService = trackedTagService;
        this.ignoredTagDtoService = ignoredTagDtoService;
    }

    @PostMapping("/{id}/tracked")
    @ApiOperation("Добавление тега в отслеживаемые пользователем")
    @ApiResponse(responseCode = "200", description = "Тег добавлен в отслеживаемые")
    @ApiResponse(responseCode = "400", description = "Тег уже добавлен в отслеживаемые")
    @ApiResponse(responseCode = "404", description = "Тег не найден")
    public ResponseEntity<TagDto> addTagToTracked(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        try {
            Optional<TagDto> tagDtoOptional = trackedTagService.saveTrackedTagByTagAndUser(id, user);
            return tagDtoOptional.map(tagDto -> new ResponseEntity<>(tagDto, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (TagNotFoundException e) {
            return new ResponseEntity<>(new TagDto(), HttpStatus.NOT_FOUND);
        } catch (TagAlreadyExistsException e) {
            return new ResponseEntity<>(new TagDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ignored")
    @ApiOperation("Возвращает все теги, который пользователь добавил в игнор")
    @ApiResponse(responseCode = "200", description = "Теги успешно получены")
    public ResponseEntity<List<IgnoredTagDto>> getUserIgnoredTags(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ignoredTagDtoService.getIgnoredTagsByUserId(user.getId()));
    }

}

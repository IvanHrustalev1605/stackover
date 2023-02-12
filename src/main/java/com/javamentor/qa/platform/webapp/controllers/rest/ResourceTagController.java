package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/tag/")
public class ResourceTagController {

    private final TagService tagService;
    private final TrackedTagService trackedTagService;
    private final TagDtoService tagDtoService;

    public ResourceTagController(TagService tagService,
                                 TrackedTagService trackedTagService,
                                 TagDtoService tagDtoService) {
        this.tagService = tagService;
        this.trackedTagService = trackedTagService;
        this.tagDtoService = tagDtoService;
    }

    @PostMapping("/{id}/tracked")
    @ApiOperation("Добавление тега в отслеживаемые пользователем")
    @ApiResponse(responseCode = "200", description = "Тег добавлен в отслеживаемые")
    @ApiResponse(responseCode = "404", description = "Тег не найден")
    public ResponseEntity<TagDto> addTagToTracked(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {

        if (!tagService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!trackedTagService.existTrackedTadByUser(id, user.getId())) {
            TrackedTag trackedTag = new TrackedTag(tagService.getById(id).get(), user);
            trackedTagService.persist(trackedTag);
        } else {
            return ResponseEntity.badRequest().build();
        }

        return tagDtoService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

}

package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.converters.TagConverter;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/tag/")
public class ResourceTagController {

    private final TagConverter tagConverter;
    private final TrackedTagService trackedTagService;

    @Operation(summary = "добавляет тег в отслеживаемые теги")
    @ApiResponse(responseCode = "200", description = "успешно",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TagDto.class)))
    @ApiResponse(responseCode = "404", description = "тег с таким id не найден")
    @ApiResponse(responseCode = "400", description = "тег уже был добавлен в отслеживаемые ранее")
    @PostMapping("/{id}/tracked")
    public ResponseEntity<TagDto> addTagToTracked(@PathVariable(name = "id") Long tagId,
                                                  @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(tagConverter.tagToTagDto(trackedTagService.add(tagId, user)));
    }
}

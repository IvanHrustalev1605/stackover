package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/api/user/tag/")
public class ResourceTagController {
    private final TagService tagService;

    private final TrackedTagService trackedTagService;

    public ResourceTagController(TagService tagService, TrackedTagService trackedTagService) {
        this.tagService = tagService;
        this.trackedTagService = trackedTagService;
    }


    @PostMapping("/{id}/tracked")
    @ApiOperation("Добавление тега в отслеживаемые")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тег успешно добавлен в отслеживаемые"),
            @ApiResponse(responseCode = "414", description = "Тег не найден")
    })

    public ResponseEntity<TagDto> trackedTag(@PathVariable Long id) {
        User user;
        try {
            Optional<Tag> tag = tagService.getById(id);
            if (tag.isPresent()) {
                user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Tag tag1 = tag.get();
                TrackedTag trackedTag = new TrackedTag(tag1, user);
                trackedTagService.persist(trackedTag);
                TagDto tagDto = new TagDto();
                tagDto.setId(id);
                tagDto.setDescription(tag1.getDescription());
                tagDto.setName(tag1.getName());

                return ResponseEntity.ok(tagDto);

            }

        } catch (Exception e) {

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/user/tag/")
public class ResourceTagController {
    private final TagService tagService;

    private final TrackedTagService trackedTagService;

    private final TagDtoService tagDtoService;



    public ResourceTagController(TagService tagService, TrackedTagService trackedTagService, TagDtoService tagDtoService) {
        this.tagService = tagService;
        this.trackedTagService = trackedTagService;
        this.tagDtoService = tagDtoService;
    }


    @GetMapping("/related")
    @Operation(summary = "Возвращает список топ 10 вопросов")
    @ApiResponse(responseCode = "200", description = "Список вопросов успешно найден")
    @ApiResponse(responseCode = "404", description = "Список не найден")
    public ResponseEntity<List<RelatedTagDto>> getTop10Tags() {
        List<RelatedTagDto> tagDtoList = tagDtoService.getTopTags();
        if (tagDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tagDtoList);
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

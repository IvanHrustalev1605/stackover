package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.converters.TagConverter;
import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.exception.TagAlreadyExistsException;
import com.javamentor.qa.platform.exception.TagNotFoundException;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/user/tag/")
public class ResourceTagController {

    private final TagService tagService;
    private final TagConverter tagConverter;
    private final TrackedTagService trackedTagService;
    private final TagDtoService tagDtoService;
    private final IgnoredTag ignoredTag;
    private final IgnoredTagDao ignoredTagDao;


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

    @GetMapping("/ignored")
    @Operation(summary = "Вывод списка игнорируемых тегов пользователя")
    @ApiResponse(responseCode = "200", description = "Список игнорируемых тегов пользователя сформирован")
    public ResponseEntity<List<IgnoredTagDto>> getAllIgnoredTag() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(tagDtoService.getAllIgnoredTags(user.getId()));
    }
    @ApiOperation(
            value = "Добавляет тэг с tagId=* в игнорируемые текущим пользователем")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Тэг с tagId=* добавлен в игнорируемые"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Неверный формат введенного tagId"),
            @io.swagger.annotations.ApiResponse(code = 404, message = "Тэг с tagId=* не найден")
    })
    @PostMapping("/{id}/ignored")
    public ResponseEntity<?> addIgnoredTag(@PathVariable("id") Long tagId,
                                           @AuthenticationPrincipal User user) {
        if(tagService.getById(tagId).isEmpty()) {
            throw new TagNotFoundException("тег с таким id не найден");
        }
        if (ignoredTagDao.getIgnoredTagByTagIdAndUserId(tagId, user.getId()).isPresent()) {
            throw new TagAlreadyExistsException("тег уже был добавлен в игнорируемые ранее");
        }
        return ResponseEntity.ok(tagConverter.tagToTagDto(ignoredTag.getIgnoredTag()));
        }
    }


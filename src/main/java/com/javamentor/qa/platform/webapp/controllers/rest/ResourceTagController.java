package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/tag")
public class ResourceTagController {

    private final IgnoredTagService ignoredTagService;
    private final TagService tagService;
    private final TagDtoService tagDtoService;

    public ResourceTagController(IgnoredTagService ignoredTagService, TagService tagService, TagDtoService tagDtoService) {
        this.ignoredTagService = ignoredTagService;
        this.tagService = tagService;
        this.tagDtoService = tagDtoService;
    }

    @ApiOperation(value = "Добавление тега в список игнорируемых.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Тег успешно добавлен в список игнорируемых для текущего пользователя."),
            @ApiResponse(code = 404, message = "Запрошенного тега не существует.") })
    @Transactional
    @PostMapping("/{id}/ignored")
    public ResponseEntity<TagDto> addTagToIgnoreList(@Parameter(description = "id тега, который нужно добавить в игнор.")
                                                     @PathVariable("id") Long tagId,
                                                     @Parameter(description = "Пользователь прошедший аутентификацию")
                                                     @AuthenticationPrincipal User user) {
        Optional<Tag> requestedTag = tagService.getById(tagId);
        if (requestedTag.isPresent()) {
            ignoredTagService.persist(new IgnoredTag(tagService.getById(tagId).get(), user));
            return new ResponseEntity<>(tagDtoService.getById(tagId).get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

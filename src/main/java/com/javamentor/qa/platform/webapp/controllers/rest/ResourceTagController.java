package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/user/tag")
public class ResourceTagController {

    private final IgnoredTagService ignoredTagService;
    private final TagDtoService tagDtoService;

    public ResourceTagController(IgnoredTagService ignoredTagService, TagDtoService tagDtoService) {
        this.ignoredTagService = ignoredTagService;
        this.tagDtoService = tagDtoService;
    }

    @ApiOperation(value = "Добавление тега в список игнорируемых.")
    @ApiResponses(value =
    @ApiResponse(code = 200, message = "Тег успешно добавлен в список игнорируемых для текущего пользователя."))
    @PostMapping("/{id}/ignored")
    public ResponseEntity<TagDto> addTagToIgnoreList(@Parameter(description = "id тега, который нужно добавить в игнор.")
                                                     @PathVariable("id") Long tagId,
                                                     @Parameter(description = "Пользователь прошедший аутентификацию")
                                                     @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(ignoredTagService.addTagToIgnoreList(tagId, user).get(), HttpStatus.OK);
    }

    @GetMapping("/related")
    @ApiOperation(value = "Получает список топ 10 DTO тегов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список  топ 10 тегов DTO успешно получен"),
            @ApiResponse(code = 400, message = "Неправильный запрос")
    })
    public ResponseEntity<List<RelatedTagDto>> getTop10Tags() {
        Optional<List<RelatedTagDto>> relatedTagDtoList = tagDtoService.getTopTags();
        return relatedTagDtoList.map(relatedTagDos -> new ResponseEntity<>(relatedTagDos, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

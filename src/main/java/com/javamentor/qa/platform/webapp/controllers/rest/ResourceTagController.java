package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
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


@AllArgsConstructor
@RestController
@RequestMapping("/api/user/tag")
public class ResourceTagController {

    private final TrackedTagService trackedTagService;
    private final IgnoredTagService ignoredTagService;
    private final TagDtoService tagDtoService;


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


    @GetMapping("/tracked")
    @ApiOperation(value = "Возвращает все теги пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное получение данных"),
            @ApiResponse(code = 404, message = "У пользователя нет отслеживаемых тегов")
    })
    public ResponseEntity<List<TrackedTagDto>> getListUserTrackedDto(@AuthenticationPrincipal User user) {
        return trackedTagService.getListUserTrackedDto(user) != null
                ? new ResponseEntity<>(trackedTagService.getListUserTrackedDto(user), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}

package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.exception.ApiNotFoundException;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/tag/related")
@RequiredArgsConstructor
@Api(value = "Контроллер связанных тегов")
public class ResourceTagController {

    private final TagDtoService tagDtoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Получить топ-10 тегов", response = RelatedTagDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Топ теги получены успешно"),
            @ApiResponse(responseCode = "404", description = "Список тегов не найден")
    })
    public ResponseEntity<List<RelatedTagDto>> getTop10Tags() {
        List<RelatedTagDto> topTags = tagDtoService.getTopTags();

        if (topTags.isEmpty()) {
            throw new ApiNotFoundException("Список тегов пуст");
        }

        return ResponseEntity.ok(topTags);
    }
    @Schema(description = "Получение топ 3х тэгов, в которых было набрано больше всего баллов при ответе на вопросы пользователем")
    @GetMapping("/top-3-tags")
    public ResponseEntity getTopTags(@AuthenticationPrincipal User user) {
        List<TagDto> top3TagsByUserId = tagDtoService.getTop3TagsByUserId(user.getId());
        if (top3TagsByUserId.isEmpty()) {
            throw new ApiNotFoundException("Список тэгов пуст. Возможно не было ответов, приносящих репутацию");
        }
        return new ResponseEntity<>(top3TagsByUserId,HttpStatus.OK);
    }
}

package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/tag/")
@RequiredArgsConstructor
public class ResourceTagController {

    private final TagDtoService tagDtoService;

    @GetMapping("/related")
    @Operation(summary = "Получаем топ 10 вопросов")
    @ApiResponse(responseCode = "200", description = "Список вопросов найден")
    @ApiResponse(responseCode = "404", description = "Список не найден")
    public ResponseEntity<List<RelatedTagDto>> getTop10Tags() {
        List<RelatedTagDto> tagDtoList = tagDtoService.getTopTags();
        if (tagDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tagDtoList);
    }
}

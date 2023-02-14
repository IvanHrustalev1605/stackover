package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.IgnoredTagDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/tag")
public class ResourceTagController {
    private final IgnoredTagDtoService ignoredTagDtoService;

    public ResourceTagController(IgnoredTagDtoService ignoredTagDtoService) {
        this.ignoredTagDtoService = ignoredTagDtoService;
    }

    @GetMapping("/ignored")
    @ApiOperation("Возвращает все теги, который пользователь добавил в игнор")
    @ApiResponse(responseCode = "200", description = "Теги успешно получены")
    public ResponseEntity<List<IgnoredTagDto>> getUserIgnoredTags(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ignoredTagDtoService.getIgnoredTagsByUserId(user.getId()));
    }
}

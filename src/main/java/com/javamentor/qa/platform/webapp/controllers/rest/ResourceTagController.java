package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/tag/top-3tags")
public class ResourceTagController {
    private final TagDtoService tagDtoService;

    public ResourceTagController(TagDtoService tagDtoService) {
        this.tagDtoService = tagDtoService;
    }

    @GetMapping
    public ResponseEntity getTopTags(@RequestParam ("id") Long id) {
        return new ResponseEntity<>(tagDtoService.getTop3TagsByUserId(id),HttpStatus.OK);
    }
}

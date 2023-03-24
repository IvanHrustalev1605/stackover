package com.javamentor.qa.platform.models.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IgnoredTagDto {
    private final Long id;

    @NotNull
    private final String name;
}

package com.javamentor.qa.platform.models.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.javamentor.qa.platform.models.entity.question.Tag} entity
 */
@Data
public class TagDto implements Serializable {
    private final Long id;
    @NotNull
    private final String name;
    private final String description;
}